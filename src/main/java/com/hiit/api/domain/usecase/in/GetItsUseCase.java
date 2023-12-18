package com.hiit.api.domain.usecase.in;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.dao.it.registerd.RegisteredItData;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dto.request.it.GetItsUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.service.in.MemberRegisteredItRelationService;
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

	private final RegisteredItDao registeredItDao;
	private final ItRelationDao itRelationDao;
	private final MemberRegisteredItRelationService memberRegisteredItRelationService;

	@Override
	@Transactional(readOnly = true)
	public ItInfos execute(GetItsUseCaseRequest request) {
		Long memberId = request.getMemberId();

		List<RegisteredItData> registeredIts = getRegisteredIt();
		List<Long> memberRegisteredItIds = getMemberRegisteredItIds(memberId);

		List<ItInfo> source = new ArrayList<>();
		for (RegisteredItData registeredIt : registeredIts) {
			Long inMemberCount = itRelationDao.countByTargetItId(registeredIt.getId());
			boolean memberIn = memberRegisteredItIds.contains(registeredIt.getId());
			source.add(makeItInfo(registeredIt, inMemberCount, memberIn));
		}
		return buildResponse(source);
	}

	private List<RegisteredItData> getRegisteredIt() {
		return registeredItDao.findAll();
	}

	private List<Long> getMemberRegisteredItIds(Long memberId) {
		return memberRegisteredItRelationService.browse(memberId).stream()
				.map(ItRelationData::getTargetId)
				.collect(Collectors.toList());
	}

	private ItInfo makeItInfo(RegisteredItData registeredIt, Long inMemberCount, boolean memberIn) {
		return ItInfo.builder()
				.id(registeredIt.getId())
				.topic(registeredIt.getTopic())
				.startTime(registeredIt.getStartTime())
				.endTime(registeredIt.getEndTime())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.build();
	}

	private ItInfos buildResponse(List<ItInfo> source) {
		return new ItInfos(source);
	}
}
