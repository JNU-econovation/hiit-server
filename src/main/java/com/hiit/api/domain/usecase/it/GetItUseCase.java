package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.model.it.registered.BasicIt;
import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.service.it.BrowseItsService;
import com.hiit.api.domain.service.it.BrowseMemberInItRelationService;
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

	private final BrowseItsService browseItsService;
	private final BrowseMemberInItRelationService browseMemberInItRelationService;

	private final ItRelationDao itRelationDao;

	@Override
	@Transactional(readOnly = true)
	public ItInfo execute(final GetItUseCaseRequest request) {
		final Long registeredItId = request.getItId();
		final Long memberId = request.getMemberId();

		log.debug("get it : m - {}, i - {}", memberId, registeredItId);
		BasicIt source = getSource(registeredItId);

		log.debug("get member in it count : i - {}", registeredItId);
		Long inMemberCount = calcInMemberCount(registeredItId);
		log.debug("get member in it ids : m - {}", memberId);
		List<ItRelation> memberRegisteredIts = browseMemberInRegisteredIts(memberId);

		for (ItRelation memberRegisteredIt : memberRegisteredIts) {
			if (memberRegisteredIt.isTarget(registeredItId)) {
				return buildResponse(source, inMemberCount, true);
			}
		}
		return buildResponse(source, inMemberCount, false);
	}

	private BasicIt getSource(Long itId) {
		return browseItsService.browse(itId);
	}

	private ItInfo buildResponse(BasicIt it, Long inMemberCount, boolean memberIn) {
		return ItInfo.builder()
				.id(it.getId())
				.topic(it.getTopic())
				.startTime(it.getStartTime())
				.endTime(it.getEndTime())
				.inMemberCount(inMemberCount)
				.memberIn(memberIn)
				.build();
	}

	private Long calcInMemberCount(Long targetId) {
		return itRelationDao.countByTargetItId(targetId);
	}

	private List<ItRelation> browseMemberInRegisteredIts(Long memberId) {
		return browseMemberInItRelationService.browse(memberId);
	}
}
