package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.GetEndItsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.end.EndItTimeDetails;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.member.GetMemberId;
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

	private final InItDao dao;
	private final InItEntityConverter entityConverter;

	@Override
	@Transactional(readOnly = true)
	public EndItInfos execute(final GetEndItsUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;

		List<InIt> sources = getSources(memberId);

		List<EndItInfo> endItInfos = new ArrayList<>();
		for (InIt source : sources) {
			if (!source.isOwner(memberId)) {
				continue;
			}
			ItTimeDetails timeInfo = extractTimeInfo(source);
			endItInfos.add(makeEndItInfo(source, timeInfo));
		}
		return buildResponse(endItInfos);
	}

	private List<InIt> getSources(GetMemberId memberId) {
		return dao.findAllEndStatusByMember(memberId.getId()).stream()
				.map(entityConverter::from)
				.collect(Collectors.toList());
	}

	private EndItInfos buildResponse(List<EndItInfo> sources) {
		return new EndItInfos(sources);
	}

	private ItTimeDetails extractTimeInfo(InIt inIt) {
		ItTimeDetails source = inIt.getTime();
		return EndItTimeDetails.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}

	private EndItInfo makeEndItInfo(InIt source, ItTimeDetails timeInfo) {
		return EndItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.build();
	}
}
