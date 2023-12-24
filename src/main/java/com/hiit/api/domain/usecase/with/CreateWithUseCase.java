package com.hiit.api.domain.usecase.with;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.domain.dao.support.PeriodUtils;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.dto.request.with.CreateWithUseCaseRequest;
import com.hiit.api.domain.exception.CreateCountPolicyException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.exception.TimePolicyException;
import com.hiit.api.domain.service.with.WithItTimeInfo;
import com.hiit.api.domain.service.with.WithItTimeInfoService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateWithUseCase implements AbstractUseCase<CreateWithUseCaseRequest> {

	private static final int MAX_CREATE_COUNT = 1;

	private final WithDao withDao;
	private final InItDao inItDao;
	private final ItRelationDao itRelationDao;

	private final WithItTimeInfoService withItTimeInfoService;

	@Override
	@Transactional
	public ServiceResponse execute(CreateWithUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long inItId = request.getInItId();
		String content = request.getContent();

		InItData inIt = readInIt(inItId, memberId);
		Long targetMemberId = inIt.getMemberId();
		if (!memberId.equals(targetMemberId)) {
			throw new MemberAccessDeniedException("not match memberId");
		}

		ItRelationData itRelation = readItRelation(inIt.getId());
		WithItTimeInfo timeInfoSource = readTimeSource(itRelation);
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeInfoSource, now);
		if (!period.isValid(now)) {
			throw new TimePolicyException(period, now);
		}

		Optional<WithData> with = getWith(itRelation, memberId, period);

		if (with.isPresent()) {
			throw new CreateCountPolicyException(MAX_CREATE_COUNT);
		}
		WithData source = buildSaveSource(content, itRelation);
		withDao.save(source);
		return ServiceResponse.VOID;
	}

	private Optional<WithData> getWith(ItRelationData itRelation, Long memberId, Period period) {
		return withDao.findByInItEntityAndMemberAndPeriod(itRelation.getInItId(), memberId, period);
	}

	private WithData buildSaveSource(String content, ItRelationData itRelation) {
		return WithData.builder().content(content).inItId(itRelation.getInItId()).build();
	}

	private InItData readInIt(Long inItId, Long memberId) {
		return inItDao.findActiveStatusByIdAndMember(inItId, memberId);
	}

	private ItRelationData readItRelation(Long inItId) {
		return itRelationDao.findByInItId(inItId);
	}

	private WithItTimeInfo readTimeSource(ItRelationData itRelation) {
		return withItTimeInfoService.read(itRelation.getId());
	}
}
