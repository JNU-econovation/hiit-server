package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dto.request.end.GetEndItsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.service.end.it.ItTimeInfo;
import com.hiit.api.domain.service.end.it.ItTimeInfoService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndItsUseCase implements AbstractUseCase<GetEndItsUseCaseRequest> {

	private final InItDao inItDao;
	private final ItTimeInfoService itTimeInfoService;

	@Override
	@Transactional(readOnly = true)
	public EndItInfos execute(GetEndItsUseCaseRequest request) {
		Long memberId = request.getMemberId();

		List<InItData> endInIts = getEndInIts(memberId);

		List<EndItInfo> source = new ArrayList<>();
		for (InItData endInIt : endInIts) {
			ItTimeInfo timeInfo = readTimeInfo(endInIt.getItRelationId());
			source.add(makeEndItInfo(endInIt, timeInfo));
		}
		return buildResponse(source);
	}

	private List<InItData> getEndInIts(Long memberId) {
		return inItDao.findAllEndStatusByMember(memberId);
	}

	private EndItInfos buildResponse(List<EndItInfo> source) {
		return new EndItInfos(source);
	}

	private ItTimeInfo readTimeInfo(Long id) {
		return itTimeInfoService.read(id);
	}

	private EndItInfo makeEndItInfo(InItData endInItData, ItTimeInfo timeInfo) {
		return EndItInfo.builder()
				.id(endInItData.getId())
				.title(endInItData.getTitle())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.build();
	}
}
