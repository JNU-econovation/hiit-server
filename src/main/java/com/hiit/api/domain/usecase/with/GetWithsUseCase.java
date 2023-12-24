package com.hiit.api.domain.usecase.with;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dao.member.MemberData;
import com.hiit.api.domain.dao.support.PageData;
import com.hiit.api.domain.dao.support.PageableInfo;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.dto.request.with.GetWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.service.with.WithInfoAssembleService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetWithsUseCase implements AbstractUseCase<GetWithsUseCaseRequest> {

	private final WithDao withDao;
	private final InItDao inItDao;
	private final MemberDao memberDao;

	private final WithInfoAssembleService withInfoAssembleService;

	@Override
	@Transactional(readOnly = true)
	public WithPage execute(GetWithsUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long inItId = request.getInItId();
		Boolean isMember = request.getIsMember();
		PageableInfo pageable = request.getPageable();

		MemberData member = readMember(memberId);
		InItData inIt = browseInIt(inItId, memberId);
		if (inIt.isOwner(memberId)) {
			throw new MemberAccessDeniedException("not match memberId");
		}
		WithMemberInfo memberInfo = makeMemberInfo(member, inIt);
		GetWithElements getWithElements = makeGetWithElements(isMember, inIt, pageable, memberId);

		PageData<WithData> source = getWiths(getWithElements);

		PageImpl<WithInfo> withInfoPage =
				withInfoAssembleService.execute(source, inIt, memberInfo, pageable);
		return buildResponse(withInfoPage);
	}

	private PageData<WithData> getWiths(GetWithElements elements) {
		if (elements.isMember()) {
			return getWiths(elements.getInIt(), elements.getPageableInfo(), elements.getMemberId());
		}
		return getWiths(elements.getInIt(), elements.getPageableInfo());
	}

	private PageData<WithData> getWiths(InItData inIt, PageableInfo pageable) {
		return withDao.findAllByInIt(inIt.getId(), pageable);
	}

	private PageData<WithData> getWiths(InItData inIt, PageableInfo pageable, Long memberId) {
		return withDao.findAllByInItAndMember(inIt.getId(), pageable, memberId);
	}

	private WithPage buildResponse(PageImpl<WithInfo> withInfoPage) {
		return new WithPage(withInfoPage);
	}

	private MemberData readMember(Long memberId) {
		return memberDao
				.findById(memberId)
				.orElseThrow(() -> new DataNotFoundException("Member id : " + memberId));
	}

	private InItData browseInIt(Long inItId, Long memberId) {
		return inItDao.findActiveStatusByIdAndMember(inItId, memberId);
	}

	private WithMemberInfo makeMemberInfo(MemberData member, InItData inIt) {
		return WithMemberInfo.builder()
				.name(member.getNickName())
				.profile(member.getProfile())
				.resolution(inIt.getResolution())
				.build();
	}

	private GetWithElements makeGetWithElements(
			Boolean isMember, InItData inIt, PageableInfo pageable, Long memberId) {
		if (isMember) {
			return GetWithElements.builder().inIt(inIt).pageableInfo(pageable).memberId(memberId).build();
		}
		return GetWithElements.builder().inIt(inIt).pageableInfo(pageable).build();
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	@SuperBuilder(toBuilder = true)
	private static class GetWithElements {

		private InItData inIt;
		private PageableInfo pageableInfo;
		private Long memberId;

		public boolean isMember() {
			return memberId != null;
		}
	}
}
