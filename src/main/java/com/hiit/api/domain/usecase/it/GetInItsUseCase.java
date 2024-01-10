package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.it.GetInItsUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ItInMemberCountService;
import com.hiit.api.domain.service.it.ItQueryManager;
import com.hiit.api.domain.service.it.ItRelationQuery;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.repository.entity.business.it.InItEntity;
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
public class GetInItsUseCase implements AbstractUseCase<GetInItsUseCaseRequest> {

	private final InItDao dao;
	private final InItEntityConverter entityConverter;

	private final ItRelationQuery itRelationQuery;
	private final ItQueryManager itQueryManager;

	private final ItInMemberCountService itInMemberCountService;

	@Override
	@Transactional
	public InItInfos execute(GetInItsUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;

		List<InIt> sources = getSource(memberId);
		List<InItInfo> inItInfos = new ArrayList<>();
		for (InIt source : sources) {
			if (!source.isOwner(memberId)) {
				throw new MemberAccessDeniedException(memberId.getId(), source.getId());
			}

			It_Relation itRelation = itRelationQuery.query(source::getItRelationId);

			ItTypeDetails type = itRelation.getType();
			Long inMemberCount = itInMemberCountService.execute(itRelation::getItId);
			String topic = itQueryManager.query(type, itRelation::getInItId).getTopic();
			InItInfo inItInfo = makeInItInfo(source, type.getValue(), topic, inMemberCount);
			inItInfos.add(inItInfo);
		}
		return buildResponse(inItInfos);
	}

	private List<InIt> getSource(GetMemberId memberId) {
		List<InItEntity> inIts = dao.findAllActiveStatusByMember(memberId.getId());
		return inIts.stream().map(entityConverter::from).collect(Collectors.toList());
	}

	private InItInfos buildResponse(List<InItInfo> inItInfos) {
		return new InItInfos(inItInfos);
	}

	private InItInfo makeInItInfo(InIt source, String type, String topic, Long inMemberCount) {
		return InItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.topic(topic)
				.startTime(source.getTime().getStartTime())
				.endTime(source.getTime().getEndTime())
				.days(source.getDayCode().getDays())
				.inMemberCount(inMemberCount)
				.type(type)
				.build();
	}
}
