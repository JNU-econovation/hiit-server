package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.GetEndItsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.model.ItTimeInfo;
import com.hiit.api.domain.model.it.end.EndItTimeInfo;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
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
public class GetEndItsUseCase implements AbstractUseCase<GetEndItsUseCaseRequest> {

	private final InItDao inItDao;
	private final InItEntityConverter entityConverter;

	@Override
	@Transactional(readOnly = true)
	public EndItInfos execute(final GetEndItsUseCaseRequest request) {
		final Long memberId = request.getMemberId();

		log.debug("get end inits : m - {}", memberId);
		List<InIt> sources = getSources(memberId);

		List<EndItInfo> endItInfos = new ArrayList<>();
		for (InIt source : sources) {
			ItTimeInfo timeInfo = extractTimeInfo(source);
			endItInfos.add(makeEndItInfo(source, timeInfo));
		}
		log.debug("end init size : {}", endItInfos.size());
		return buildResponse(endItInfos);
	}

	private List<InIt> getSources(Long memberId) {
		return inItDao.findAllEndStatusByMember(memberId).stream()
				.map(entityConverter::from)
				.collect(Collectors.toList());
	}

	private EndItInfos buildResponse(List<EndItInfo> sources) {
		return new EndItInfos(sources);
	}

	private ItTimeInfo extractTimeInfo(InIt inIt) {
		ItTimeInfo source = inIt.getTimeInfo();
		return EndItTimeInfo.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}

	private EndItInfo makeEndItInfo(InIt source, ItTimeInfo timeInfo) {
		return EndItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.build();
	}
}
