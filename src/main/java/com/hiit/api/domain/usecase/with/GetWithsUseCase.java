package com.hiit.api.domain.usecase.with;

import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.request.with.GetWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.service.it.in.InItTimeDetailsQueryManager;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.service.with.WithInfoAssembleService;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

	private final WithDao dao;
	private final WithEntityConverterImpl entityConverter;
	private final WithInfoAssembleService withInfoAssembleService;

	private final MemberQuery memberQuery;
	private final InItTimeDetailsQueryManager inItQueryManager;

	@Override
	@Transactional(readOnly = true)
	public WithPage execute(final GetWithsUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getInItId;
		final Boolean isMemberFilter = request.getIsMember();
		final PageRequest pageable = request.getPageable();

		InIt inIt = inItQueryManager.query(InItStatusDetails.ACTIVE, inItId, memberId);
		if (inIt.isOwner(memberId)) {
			throw new MemberAccessDeniedException(memberId.getId(), inItId.getId());
		}
		GetWithElements getWithElements = makeGetWithElements(isMemberFilter, inIt, pageable, memberId);

		PageElements<With> sources = getSources(getWithElements);

		Member member = memberQuery.query(memberId);
		WithMemberInfo memberInfo = makeMemberInfo(member, inIt);
		PageImpl<WithInfo> withInfos =
				withInfoAssembleService.execute(sources, inIt, memberInfo, pageable);

		return buildResponse(withInfos);
	}

	private PageElements<With> getSources(GetWithElements elements) {
		ItTimeDetails timeInfo = elements.getTimeInfo();
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeInfo, now);
		if (elements.isMember()) {
			return getSources(
					elements.getInIt(), elements.getPageRequest(), elements::getMemberId, period);
		}
		return getSources(elements.getInIt(), elements.getPageRequest(), period);
	}

	private PageElements<With> getSources(InIt inIt, PageRequest pageable, Period period) {
		PageElements<WithEntity> source = dao.findAllByInIt(inIt.getId(), pageable, period);
		List<With> data =
				source.getData().stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(source, data);
	}

	private PageElements<With> getSources(
			InIt inIt, PageRequest pageable, GetMemberId memberId, Period period) {
		PageElements<WithEntity> source =
				dao.findAllByInItAndMember(inIt.getId(), pageable, memberId.getId(), period);
		List<With> data =
				source.getData().stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(source, data);
	}

	private WithPage buildResponse(PageImpl<WithInfo> withInfoPage) {
		return new WithPage(withInfoPage);
	}

	private GetWithElements makeGetWithElements(
			Boolean isMember, InIt inIt, PageRequest pageable, GetMemberId memberId) {
		InItTimeDetails source = inIt.getTime();
		ItTimeDetails timeInfo =
				WithItTimeDetails.builder()
						.startTime(source.getStartTime())
						.endTime(source.getEndTime())
						.build();
		if (isMember) {
			return GetWithElements.builder()
					.inIt(inIt)
					.timeInfo(timeInfo)
					.pageRequest(pageable)
					.memberId(memberId.getId())
					.build();
		}
		return GetWithElements.builder().inIt(inIt).pageRequest(pageable).timeInfo(timeInfo).build();
	}

	private WithMemberInfo makeMemberInfo(Member member, InIt inIt) {
		return WithMemberInfo.builder()
				.name(member.getNickName())
				.profile(member.getProfile())
				.resolution(inIt.getResolution())
				.build();
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	@SuperBuilder(toBuilder = true)
	private static class GetWithElements {

		private Long memberId;
		private InIt inIt;
		private PageRequest pageRequest;
		private ItTimeDetails timeInfo;

		public boolean isMember() {
			return memberId != null;
		}
	}
}
