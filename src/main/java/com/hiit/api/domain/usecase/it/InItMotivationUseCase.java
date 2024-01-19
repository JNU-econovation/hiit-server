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
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.service.it.ItTypeQueryManager;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
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
	private static final String HIT_COUNT_STR = "현재 %d명이 힛 했습니다.";

	private final InItDao inItDao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper itTimeDetailsMapper;
	private final InItEntityConverter inItEntityConverter;

	private final ItTypeQueryManager itTypeQueryManager;
	private final WithDao withDao;
	private final HitDao hitDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public ItMotivations execute(InItMotivationUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getInItId;

		InIt inIt = readInIt(inItId);
		if (!inIt.isOwner(memberId)) {
			throw new MemberAccessDeniedException();
		}
		Long inMemberCount = itRelationDao.countByItIdAndStatus(inIt.getItId(), ItStatus.ACTIVE);

		Long inWithCount = withDao.countByInIt(inIt.getId());

		BasicIt it =
				itTypeQueryManager.query(ItTypeDetails.of(inIt.getItType()), (GetInItId) inIt::getId);
		Period period = makePeriod(it);
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

	private InIt readInIt(GetInItId inItId) {
		Optional<InItEntity> source = inItDao.findById(inItId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.key, inItId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		ItRelationEntity itRelation =
				itRelationDao.findByInItIdAndStatus(inIt.getId(), ItStatus.ACTIVE).orElse(null);
		assert itRelation != null;
		String info = inIt.getInfo();
		InItTimeDetails timeDetails = itTimeDetailsMapper.read(info, InItTimeDetails.class);
		return inItEntityConverter.from(inIt, itRelation, timeDetails);
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
