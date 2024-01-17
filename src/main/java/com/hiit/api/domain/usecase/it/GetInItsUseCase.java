package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.GetInItsUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.service.it.ItActiveMemberCountService;
import com.hiit.api.domain.service.it.ItQueryManager;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetInItsUseCase implements AbstractUseCase<GetInItsUseCaseRequest> {

	private final InItDao inItDao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper itTimeDetailsMapper;
	private final InItEntityConverter itEntityConverter;

	private final ItQueryManager itQueryManager;
	private final ItActiveMemberCountService itActiveMemberCountService;

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
			ItTypeDetails type = ItTypeDetails.of(source.getItType());
			Long activeMemberCount = itActiveMemberCountService.execute(source::getId);
			BasicIt it = itQueryManager.query(type, (GetInItId) source::getId);
			String topic = it.getTopic();
			InItInfo inItInfo = makeInItInfo(source, type.getValue(), topic, activeMemberCount);
			inItInfos.add(inItInfo);
		}
		return buildResponse(inItInfos);
	}

	private List<InIt> getSource(GetMemberId memberId) {
		List<InItEntity> inIts = inItDao.findAllActiveStatusByMemberId(memberId.getId());
		List<InIt> results = new ArrayList<>();
		for (InItEntity inIt : inIts) {
			ItRelationEntity itRelation =
					itRelationDao.findByInItIdAndStatus(inIt.getId(), ItStatus.ACTIVE).orElse(null);
			assert itRelation != null;
			String info = inIt.getInfo();
			InItTimeDetails timeDetails = itTimeDetailsMapper.read(info, InItTimeDetails.class);
			results.add(itEntityConverter.from(inIt, itRelation, timeDetails));
		}
		return results;
	}

	private InItInfos buildResponse(List<InItInfo> inItInfos) {
		return new InItInfos(inItInfos);
	}

	private InItInfo makeInItInfo(InIt source, String type, String topic, Long activeMemberCount) {
		return InItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.topic(topic)
				.startTime(source.getTime().getStartTime())
				.endTime(source.getTime().getEndTime())
				.days(source.getDayCode().getDays())
				.inMemberCount(activeMemberCount)
				.type(type)
				.build();
	}
}
