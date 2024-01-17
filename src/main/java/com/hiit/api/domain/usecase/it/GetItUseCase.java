package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ActiveItRelationBrowseService;
import com.hiit.api.domain.service.it.ItActiveMemberCountService;
import com.hiit.api.domain.service.it.ItsQueryService;
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

	private final ItsQueryService itsQueryService;
	private final ActiveItRelationBrowseService activeItRelationBrowseService;

	private final ItActiveMemberCountService itActiveMemberCountService;

	@Override
	@Transactional(readOnly = true)
	public ItInfo execute(final GetItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetItId registeredItId = request::getItId;

		BasicIt source = itsQueryService.execute(registeredItId);

		Long activeMemberCount = itActiveMemberCountService.execute(source::getId);
		List<It_Relation> activeItRelations = activeItRelationBrowseService.execute(memberId);

		for (It_Relation activeItRelation : activeItRelations) {
			if (activeItRelation.isIt(registeredItId)) {
				return buildResponse(source, activeMemberCount, true);
			}
		}
		return buildResponse(source, activeMemberCount, false);
	}

	private ItInfo buildResponse(BasicIt it, Long inMemberCount, boolean memberIn) {
		return ItInfo.builder()
				.id(it.getId())
				.topic(it.getTopic())
				.startTime(it.getStartTime())
				.endTime(it.getEndTime())
				.type(it.getType().getValue())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.build();
	}
}
