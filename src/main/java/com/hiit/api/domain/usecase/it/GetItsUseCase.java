package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.GetItsUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.model.it.registered.BasicIt;
import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.service.it.BrowseItsService;
import com.hiit.api.domain.service.it.BrowseMemberInItRelationService;
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

	private final BrowseItsService browseItsService;
	private final BrowseMemberInItRelationService browseMemberInItRelationService;

	private final ItRelationDao itRelationDao;

	@Override
	@Transactional(readOnly = true)
	public ItInfos execute(final GetItsUseCaseRequest request) {
		final Long memberId = request.getMemberId();

		log.debug("get its : m - {}", memberId);
		List<BasicIt> sources = getSources();
		log.debug("it size : {}", sources.size());

		log.debug("get member in it ids : m - {}", memberId);
		List<Long> memberInItIds = browseMemberInItIds(memberId);

		List<ItInfo> its = new ArrayList<>();
		for (BasicIt source : sources) {
			Long inMemberCount = calcInMemberCount(source);
			boolean memberIn = memberInItIds.contains(source.getId());
			its.add(makeItInfo(source, inMemberCount, memberIn));
		}
		return buildResponse(its);
	}

	private List<BasicIt> getSources() {
		return browseItsService.browse();
	}

	private ItInfos buildResponse(List<ItInfo> source) {
		return new ItInfos(source);
	}

	private List<Long> browseMemberInItIds(Long memberId) {
		return browseMemberInItRelationService.browse(memberId).stream()
				.map(ItRelation::getTargetItId)
				.collect(Collectors.toList());
	}

	private Long calcInMemberCount(BasicIt it) {
		return itRelationDao.countByTargetItId(it.getId());
	}

	private ItInfo makeItInfo(BasicIt it, Long inMemberCount, boolean memberIn) {
		return ItInfo.builder()
				.id(it.getId())
				.topic(it.getTopic())
				.startTime(it.getStartTime())
				.endTime(it.getEndTime())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.build();
	}
}
