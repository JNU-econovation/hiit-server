package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.dao.it.registerd.RegisteredItData;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.service.it.MemberRegisteredItRelationService;
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

	private final RegisteredItDao registeredItDao;

	private final ItRelationDao itRelationDao;
	private final MemberRegisteredItRelationService memberRegisteredItRelationService;

	@Override
	@Transactional(readOnly = true)
	public ItInfo execute(GetItUseCaseRequest request) {
		Long registeredItId = request.getItId();
		Long memberId = request.getMemberId();

		RegisteredItData registeredIt = getRegisteredIt(registeredItId);

		Long inMemberCount = calcInMemberCount(registeredItId);
		List<ItRelationData> memberRegisteredIts = browseMemberInRegisteredIts(memberId);

		for (ItRelationData memberRegisteredIt : memberRegisteredIts) {
			if (memberRegisteredIt.isTarget(registeredItId)) {
				return buildResponse(registeredIt, inMemberCount, true);
			}
		}
		return buildResponse(registeredIt, inMemberCount, false);
	}

	private RegisteredItData getRegisteredIt(Long itId) {
		return registeredItDao
				.findById(itId)
				.orElseThrow(() -> new DataNotFoundException("RegisteredIt id : " + itId));
	}

	private ItInfo buildResponse(
			RegisteredItData registeredIt, Long inMemberCount, boolean memberIn) {
		return ItInfo.builder()
				.id(registeredIt.getId())
				.topic(registeredIt.getTopic())
				.startTime(registeredIt.getStartTime())
				.endTime(registeredIt.getEndTime())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.build();
	}

	private Long calcInMemberCount(Long targetId) {
		return itRelationDao.countByTargetItId(targetId);
	}

	private List<ItRelationData> browseMemberInRegisteredIts(Long memberId) {
		return memberRegisteredItRelationService.browse(memberId);
	}
}
