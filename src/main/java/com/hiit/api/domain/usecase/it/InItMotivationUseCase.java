package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.it.InItMotivationUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.relation.GetItRelationId;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ItQueryManager;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InItMotivationUseCase implements AbstractUseCase<InItMotivationUseCaseRequest> {

	private static final String IN_MEMBER_COUNT_STR = "현재 %d명이 참여 중입니다.";
	private static final String IN_WITH_COUNT_STR = "현재 %d명이 함께 하고 있습니다.";
	private static final String HIT_COUNT_STR = "현재 %d번 힛 했습니다.";

	private final InItDao inItDao;
	private final InItEntityConverter inItEntityConverter;
	private final ItRelationDao itRelationDao;
	private final ItRelationEntityConverter itRelationEntityConverter;
	private final ItQueryManager itQueryManager;

	private final WithDao withDao;

	private final HitDao hitDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public ItMotivations execute(InItMotivationUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getInItId;

		InIt inIt = readInIt(inItId);
		if (inIt.isOwner(memberId)) {
			throw new MemberAccessDeniedException();
		}
		It_Relation itRelation = readItRelation(inIt::getItRelationId);
		Long inMemberCount = itRelationDao.countByItId(itRelation.getItId());

		Long inWithCount = withDao.countByInIt(inIt.getId());

		BasicIt basicIt = itQueryManager.query(itRelation.getType(), itRelation::getItId);
		Period period = makePeriod(basicIt);
		Long hitCount = hitDao.countHitByInItAndPeriod(inItId.getId(), period);

		return buildResponse(inMemberCount, inWithCount, hitCount);
	}

	private ItMotivations buildResponse(Long inMemberCount, Long inWithCount, Long hitCount) {
		final String inMemberCountStr = String.format(IN_MEMBER_COUNT_STR, inMemberCount);
		final String inWithCountStr = String.format(IN_WITH_COUNT_STR, inWithCount);
		final String hitCountStr = String.format(HIT_COUNT_STR, hitCount);
		List<String> source = new ArrayList<>();
		source.add(inMemberCountStr);
		source.add(inWithCountStr);
		source.add(hitCountStr);
		return new ItMotivations(source);
	}

	private It_Relation readItRelation(GetItRelationId itRelationId) {
		Optional<ItRelationEntity> source = itRelationDao.findById(itRelationId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetItRelationId.key, itRelationId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return itRelationEntityConverter.from(source.get());
	}

	private InIt readInIt(GetInItId inItId) {
		Optional<InItEntity> source = inItDao.findById(inItId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.key, inItId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return inItEntityConverter.from(source.get());
	}

	private Period makePeriod(BasicIt basicIt) {
		LocalDateTime now = LocalDateTime.now();
		LocalTime startTime = basicIt.getStartTime();
		LocalTime endTime = basicIt.getEndTime();
		LocalDateTime startDateTime = LocalDateTime.of(now.toLocalDate(), startTime);
		LocalDateTime endDateTime = LocalDateTime.of(now.toLocalDate(), endTime);
		return Period.builder().start(startDateTime).end(endDateTime).build();
	}
}
