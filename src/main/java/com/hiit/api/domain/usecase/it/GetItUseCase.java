package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ActiveItMemberCountService;
import com.hiit.api.domain.service.it.ActiveItRelationBrowseService;
import com.hiit.api.domain.service.it.ItQueryService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetItUseCase implements AbstractUseCase<GetItUseCaseRequest> {

	private final ItQueryService itQueryService;
	private final ActiveItRelationBrowseService activeItRelationBrowseService;

	private final ItRelationDao itRelationDao;

	private final ActiveItMemberCountService activeItMemberCountService;

	@Override
	@Transactional(readOnly = true)
	public ItInfo execute(final GetItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetItId registeredItId = request::getItId;

		BasicIt source = itQueryService.execute(registeredItId);

		Long activeMemberCount = activeItMemberCountService.execute(source::getId);
		List<It_Relation> activeItRelations = activeItRelationBrowseService.execute(memberId);

		for (It_Relation activeItRelation : activeItRelations) {
			if (activeItRelation.isIt(registeredItId)) {
				return buildResponse(source, activeMemberCount, true, activeItRelations);
			}
		}
		return buildResponse(source, activeMemberCount, false);
	}

	private ItInfo buildResponse(
			BasicIt it, Long inMemberCount, boolean memberIn, List<It_Relation> activeItRelations) {
		Long inItId = -1L;
		if (memberIn) {
			It_Relation relation =
					activeItRelations.stream()
							.filter(itRelation -> itRelation.isIt(it::getId))
							.findFirst()
							.orElse(null);
			assert relation != null;
			inItId = relation.getInItId();
		}
		return ItInfo.builder()
				.id(it.getId())
				.topic(it.getTopic())
				.startTime(it.getStartTime())
				.endTime(it.getEndTime())
				.type(it.getType().getValue())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.inItId(inItId)
				.build();
	}

	private ItInfo buildResponse(BasicIt it, Long inMemberCount, boolean memberIn) {
		Long inItId = -1L;
		return ItInfo.builder()
				.id(it.getId())
				.topic(it.getTopic())
				.startTime(it.getStartTime())
				.endTime(it.getEndTime())
				.type(it.getType().getValue())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.inItId(inItId)
				.build();
	}
}
