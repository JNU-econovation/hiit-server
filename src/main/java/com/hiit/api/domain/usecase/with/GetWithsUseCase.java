package com.hiit.api.domain.usecase.with;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.request.with.GetWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.service.it.ActiveItRelationBrowseService;
import com.hiit.api.domain.service.it.ItTypeQueryManager;
import com.hiit.api.domain.service.with.WithInfoAssembleService;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.entity.business.with.WithStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

	private final ItRelationDao itRelationDao;
	private final ActiveItRelationBrowseService activeItRelationBrowseService;

	private final ItTypeQueryManager itTypeQueryManager;

	@Override
	@Transactional(readOnly = true)
	public WithPage execute(final GetWithsUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetItId itId = request::getItId;
		final Boolean isMemberFilter = request.getIsMember();
		final PageRequest pageable = request.getPageable();
		final Boolean random = request.getRandom();

		// todo type
		BasicIt it = itTypeQueryManager.query(ItTypeDetails.IT_REGISTERED, itId);
		GetWithElements getWithElements =
				makeGetWithElements(isMemberFilter, it, pageable, memberId, random);

		PageElements<With> sources = getSources(getWithElements);

		PageImpl<WithInfo> withInfos = withInfoAssembleService.execute(sources, pageable);

		return buildResponse(withInfos);
	}

	private PageElements<With> getSources(GetWithElements elements) {
		ItTimeDetails timeInfo = elements.getTimeInfo();
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeInfo, now);
		if (elements.isMember()) {
			return getSources(elements.getItId(), elements.getPageRequest(), elements::getMemberId);
		}
		if (elements.isRandom()) {
			return getSources(elements.getItId(), elements.getPageRequest().getPageSize());
		}
		return getSources(elements.getItId(), elements.getPageRequest(), period);
	}

	// 랜덤하게 윗을 가져온다.
	private PageElements<With> getSources(GetItId itId, Integer size) {
		List<ItRelationEntity> itRelations =
				itRelationDao.findAllByItIdAndStatus(itId.getId(), ItStatus.ACTIVE);
		List<PageElements<WithEntity>> pages = new ArrayList<>();
		for (ItRelationEntity itRelation : itRelations) {
			PageElements<WithEntity> page =
					dao.findAllByInItRandomAndStatus(itRelation.getInItId(), size, WithStatus.ACTIVE);
			pages.add(page);
		}
		int totalPageSize = -1;
		int totalPageNumber = -1;
		int totalPageCount = -1;
		Long totalCount = 0L;
		List<WithEntity> totalData = new ArrayList<>();
		for (PageElements<WithEntity> page : pages) {
			totalCount += page.getTotalCount();
			totalData.addAll(page.getData());
		}
		List<With> data = totalData.stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(totalPageSize, totalPageNumber, totalPageCount, totalCount, data);
	}

	// 기간을 지정하여 윗을 가져온다.
	private PageElements<With> getSources(GetItId itId, PageRequest pageable, Period period) {
		List<ItRelationEntity> itRelations =
				itRelationDao.findAllByItIdAndStatus(itId.getId(), ItStatus.ACTIVE);
		List<PageElements<WithEntity>> pages = new ArrayList<>();
		for (ItRelationEntity itRelation : itRelations) {
			PageElements<WithEntity> page =
					dao.findAllByInItAndStatus(itRelation.getInItId(), pageable, period, WithStatus.ACTIVE);
			pages.add(page);
		}
		int totalPageSize = -1;
		int totalPageNumber = -1;
		int totalPageCount = -1;
		Long totalCount = 0L;
		List<WithEntity> totalData = new ArrayList<>();
		for (PageElements<WithEntity> page : pages) {
			totalCount += page.getTotalCount();
			totalData.addAll(page.getData());
		}
		List<With> data = totalData.stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(totalPageSize, totalPageNumber, totalPageCount, totalCount, data);
	}

	// 기간을 지정하여 특정 멤버의 윗을 가져온다.
	private PageElements<With> getSources(GetItId itId, PageRequest pageable, GetMemberId memberId) {
		List<It_Relation> activeRelation =
				activeItRelationBrowseService.execute(memberId).stream().collect(Collectors.toList());
		It_Relation relation =
				activeRelation.stream()
						.filter(itRelation -> itRelation.isIt(itId))
						.findFirst()
						.orElse(null);
		assert relation != null;
		PageElements<WithEntity> source =
				dao.findAllByInItAndMemberIdAndStatus(
						relation.getInItId(), memberId.getId(), pageable, WithStatus.ACTIVE);
		List<With> data =
				source.getData().stream().map(entityConverter::from).collect(Collectors.toList());
		return new PageElements<>(source, data);
	}

	private WithPage buildResponse(PageImpl<WithInfo> withInfoPage) {
		return new WithPage(withInfoPage);
	}

	private GetWithElements makeGetWithElements(
			Boolean isMember, BasicIt it, PageRequest pageable, GetMemberId memberId, Boolean random) {
		ItTimeDetails timeInfo =
				WithItTimeDetails.builder().startTime(it.getStartTime()).endTime(it.getEndTime()).build();
		if (isMember) {
			return GetWithElements.builder()
					.itId(it::getId)
					.timeInfo(timeInfo)
					.pageRequest(pageable)
					.memberId(memberId.getId())
					.random(random)
					.build();
		}
		return GetWithElements.builder()
				.itId(it::getId)
				.pageRequest(pageable)
				.timeInfo(timeInfo)
				.random(random)
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
		private GetItId itId;
		private PageRequest pageRequest;
		private ItTimeDetails timeInfo;
		private Boolean random;

		public boolean isMember() {
			return memberId != null;
		}

		public boolean isRandom() {
			return random;
		}
	}
}
