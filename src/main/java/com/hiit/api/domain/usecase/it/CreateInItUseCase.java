package com.hiit.api.domain.usecase.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.CreateInItUseCaseRequest;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ItQueryManager;
import com.hiit.api.domain.service.it.ItRelationCommand;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.event.CreateInItEventPublisher;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateInItUseCase implements AbstractUseCase<CreateInItUseCaseRequest> {

	private final InItDao inItDao;
	private final ItRelationDao itRelationDao;

	private final ItRelationCommand itRelationCommand;

	private final MemberQuery memberQuery;
	private final ItQueryManager itQueryManager;

	private final CreateInItEventPublisher publisher;

	@Override
	@Transactional
	public AbstractResponse execute(CreateInItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetItId createTargetItId = request::getItId;
		final ItTypeDetails type = request.getType();
		final String dayCode = request.getDayCode();
		final String resolution = request.getResolution();

		GetMemberId member = memberQuery.query(memberId);
		List<Long> inAlreadyItIds = browseInAlreadyItIds(member.getId());
		if (inAlreadyItIds.contains(createTargetItId.getId())) {
			return AbstractResponse.VOID;
		}

		BasicIt it = itQueryManager.query(type, createTargetItId);
		String topic = it.getTopic();
		LocalTime startTime = it.getStartTime();
		LocalTime endTime = it.getEndTime();
		String info = "{\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}";

		InItEntity source = save(topic, resolution, dayCode, memberId, info, createTargetItId);
		publisher.publish(source.getId(), source.getHiitMember().getId(), type);
		return AbstractResponse.VOID;
	}

	private List<Long> browseInAlreadyItIds(Long memberId) {
		Set<Long> inAlreadyItIds = new HashSet<>();
		List<Long> inItIds =
				inItDao.findAllActiveStatusByMemberId(memberId).stream()
						.map(InItEntity::getId)
						.collect(Collectors.toList());
		for (Long inItId : inItIds) {
			ItRelationEntity relation =
					itRelationDao.findByInItIdAndStatus(inItId, ItStatus.ACTIVE).orElse(null);
			assert relation != null;
			inAlreadyItIds.add(relation.getItId());
		}
		return new ArrayList<>(inAlreadyItIds);
	}

	private InItEntity save(
			String topic,
			String resolution,
			String dayCode,
			GetMemberId memberId,
			String info,
			GetItId createTargetItId) {
		InItEntity entity =
				InItEntity.builder()
						.title(topic)
						.resolution(resolution)
						.dayCode(DayCodeList.of(dayCode))
						.hiitMember(HiitMemberEntity.builder().id(memberId.getId()).build())
						.info(info)
						.build();
		InItEntity inIt = inItDao.save(entity);
		itRelationCommand.save(createTargetItId, inIt);
		return inIt;
	}
}
