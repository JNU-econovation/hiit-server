package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.InItRelationBrowseService;
import com.hiit.api.domain.service.it.ItInMemberCountService;
import com.hiit.api.domain.service.it.ItsQuery;
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

	private final ItsQuery itsQuery;
	private final InItRelationBrowseService inItRelationBrowseService;

	private final ItInMemberCountService itInMemberCountService;

	@Override
	@Transactional(readOnly = true)
	public ItInfo execute(final GetItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetItId registeredItId = request::getItId;

		BasicIt source = itsQuery.query(registeredItId);

		Long inMemberCount = itInMemberCountService.execute(source::getId);
		List<It_Relation> memberRegisteredIts = inItRelationBrowseService.execute(memberId);

		for (It_Relation memberRegisteredIt : memberRegisteredIts) {
			if (memberRegisteredIt.isIt(registeredItId)) {
				return buildResponse(source, inMemberCount, true);
			}
		}
		return buildResponse(source, inMemberCount, false);
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
