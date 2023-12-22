package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dto.request.end.GetEndItUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.service.end.it.EndItTimeInfo;
import com.hiit.api.domain.service.end.it.EndItTimeInfoService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndItUseCase implements AbstractUseCase<GetEndItUseCaseRequest> {

	private final InItDao inItDao;
	private final EndItTimeInfoService endItTimeInfoService;

	@Override
	@Transactional(readOnly = true)
	public EndItInfo execute(GetEndItUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long endInItId = request.getEndInItId();

		InItData endInIt = getEndInIt(memberId, endInItId);

		EndItTimeInfo timeInfo = readTimeInfo(endInIt.getItRelationId());

		return buildResponse(endInIt, timeInfo);
	}

	private InItData getEndInIt(Long memberId, Long endInIt) {
		return inItDao.findEndStatusByIdAndMember(memberId, endInIt);
	}

	private EndItInfo buildResponse(InItData endInItData, EndItTimeInfo timeInfo) {
		return EndItInfo.builder()
				.id(endInItData.getId())
				.title(endInItData.getTitle())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.build();
	}

	private EndItTimeInfo readTimeInfo(Long id) {
		return endItTimeInfoService.read(id);
	}
}
