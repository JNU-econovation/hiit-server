package com.hiit.api.domain.usecase.with;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.request.with.GetWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.ItTimeInfo;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeInfo;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeInfo;
import com.hiit.api.domain.service.with.WithInfoAssembleService;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.in.it.InItEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.member.MemberEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

	private final WithDao withDao;
	private final WithEntityConverterImpl entityConverter;

	private final MemberDao memberDao;
	private final MemberEntityConverterImpl memberEntityConverter;

	private final InItDao inItDao;
	private final InItEntityConverterImpl inItEntityConverter;

	private final WithInfoAssembleService withInfoAssembleService;
	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public WithPage execute(final GetWithsUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long inItId = request.getInItId();
		final Boolean isMemberFilter = request.getIsMember();
		final PageRequest pageable = request.getPageable();

		log.debug("get member : m - {}", memberId);
		Member member = readMember(memberId);
		log.debug("get init : m - {}, i - {}", memberId, inItId);
		InIt inIt = readInIt(inItId, member);
		if (inIt.isOwner(member.getId())) {
			log.debug("{} is not owner of {}", memberId, inItId);
			throw new MemberAccessDeniedException(memberId, inItId);
		}
		GetWithElements getWithElements = makeGetWithElements(isMemberFilter, inIt, pageable, memberId);

		log.debug("get withs : m filter - {}, m - {}, i - {}", isMemberFilter, memberId, inItId);
		PageElements<With> sources = getSources(getWithElements);

		WithMemberInfo memberInfo = makeMemberInfo(member, inIt);
		PageImpl<WithInfo> withInfos =
				withInfoAssembleService.execute(sources, inIt, memberInfo, pageable);

		return buildResponse(withInfos);
	}

	private PageElements<With> getSources(GetWithElements elements) {
		ItTimeInfo timeInfo = elements.getTimeInfo();
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeInfo, now);
		if (elements.isMember()) {
			return getSources(
					elements.getInIt(), elements.getPageRequest(), elements.getMemberId(), period);
		}
		return getSources(elements.getInIt(), elements.getPageRequest(), period);
	}

	private PageElements<With> getSources(InIt inIt, PageRequest pageable, Period period) {
		PageElements<WithEntity> source = withDao.findAllByInIt(inIt.getId(), pageable, period);
		List<With> data =
				source.getData().stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(source, data);
	}

	private PageElements<With> getSources(
			InIt inIt, PageRequest pageable, Long memberId, Period period) {
		PageElements<WithEntity> source =
				withDao.findAllByInItAndMember(inIt.getId(), pageable, memberId, period);
		List<With> data =
				source.getData().stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(source, data);
	}

	private WithPage buildResponse(PageImpl<WithInfo> withInfoPage) {
		return new WithPage(withInfoPage);
	}

	private Member readMember(Long memberId) {
		Optional<HiitMemberEntity> source = memberDao.findById(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return memberEntityConverter.from(source.get());
	}

	private InIt readInIt(Long inItId, Member member) {
		return inItEntityConverter.from(inItDao.findActiveStatusByIdAndMember(inItId, member.getId()));
	}

	private GetWithElements makeGetWithElements(
			Boolean isMember, InIt inIt, PageRequest pageable, Long memberId) {
		InItTimeInfo source = inIt.getTimeInfo();
		ItTimeInfo timeInfo =
				WithItTimeInfo.builder()
						.startTime(source.getStartTime())
						.endTime(source.getEndTime())
						.build();
		if (isMember) {
			return GetWithElements.builder()
					.inIt(inIt)
					.timeInfo(timeInfo)
					.pageRequest(pageable)
					.memberId(memberId)
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
		private ItTimeInfo timeInfo;

		public boolean isMember() {
			return memberId != null;
		}
	}
}
