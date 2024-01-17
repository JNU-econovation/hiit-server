package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dto.request.it.GetItsUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ActiveItRelationBrowseService;
import com.hiit.api.domain.service.it.ItActiveMemberCountService;
import com.hiit.api.domain.service.it.ItsQueryService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetItsUseCase implements AbstractUseCase<GetItsUseCaseRequest> {

	private final ItsQueryService itsQueryService;
	private final ActiveItRelationBrowseService activeItRelationBrowseService;

	private final ItActiveMemberCountService itActiveMemberCountService;

	@Override
	@Transactional(readOnly = true)
	public ItInfos execute(final GetItsUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;

		List<BasicIt> sources = itsQueryService.execute();

		List<Long> memberInItIds =
				activeItRelationBrowseService.execute(memberId).stream()
						.map(It_Relation::getItId)
						.collect(Collectors.toList());

		List<ItInfo> its = new ArrayList<>();
		for (BasicIt source : sources) {
			Long activeMemberCount = itActiveMemberCountService.execute(source::getId);
			boolean memberIn = memberInItIds.contains(source.getId());
			its.add(makeItInfo(source, activeMemberCount, memberIn));
		}
		return buildResponse(its);
	}

	private ItInfos buildResponse(List<ItInfo> source) {
		return new ItInfos(source);
	}

	private ItInfo makeItInfo(BasicIt it, Long inMemberCount, boolean memberIn) {
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
