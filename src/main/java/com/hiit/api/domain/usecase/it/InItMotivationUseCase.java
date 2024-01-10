package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.it.InItMotivationUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.it.BrowseTargetItServiceManager;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.member.MemberEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
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

	private final MemberDao memberDao;
	private final MemberEntityConverter memberEntityConverter;

	private final InItDao inItDao;
	private final InItEntityConverter inItEntityConverter;
	private final ItRelationDao itRelationDao;
	private final ItRelationEntityConverter itRelationEntityConverter;
	private final BrowseTargetItServiceManager browseTargetItServiceManager;

	private final WithDao withDao;

	private final HitDao hitDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public ItMotivations execute(InItMotivationUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long inItId = request.getInItId();

		Member member = readMember(memberId);
		ItRelation itRelation = readItRelation(inItId);
		Long inMemberCount = calcInMemberCount(itRelation.getTargetItId());

		InIt inIt = readInIt(itRelation);
		if (member.isSameId(inIt.getMemberId())) {
			throw new MemberAccessDeniedException();
		}
		Long inWithCount = withDao.countByInIt(inIt.getId());

		BasicIt basicIt = readTargetIt(itRelation.getTargetItType(), itRelation.getTargetItId());
		Period period = makePeriod(basicIt);
		Long hitCount = hitDao.countHitByInItAndPeriod(inItId, period);

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

	private Member readMember(Long memberId) {
		Optional<HiitMemberEntity> source = memberDao.findById(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return memberEntityConverter.from(source.get());
	}

	private ItRelation readItRelation(Long inItId) {
		ItRelation source = itRelationEntityConverter.from(itRelationDao.findByInItId(inItId));
		if (!source.isInIt(inItId)) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("itRelation", inItId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return source;
	}

	private Long calcInMemberCount(Long targetItId) {
		return itRelationDao.countByTargetItId(targetItId);
	}

	private InIt readInIt(ItRelation itRelation) {
		Optional<InItEntity> source = inItDao.findById(itRelation.getInItId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate("inItId", itRelation.getInItId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return inItEntityConverter.from(source.get());
	}

	private BasicIt readTargetIt(TargetItTypeInfo type, Long targetItId) {
		return browseTargetItServiceManager.browse(type, targetItId);
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
