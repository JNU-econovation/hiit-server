package com.hiit.api.domain.usecase.end.it;

import static com.hiit.api.domain.model.it.relation.ItTypeDetails.IT_REGISTERED;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.end.GetEndItsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.end.EndItTimeDetails;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.service.it.ItQueryManager;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
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
public class GetEndItsUseCase implements AbstractUseCase<GetEndItsUseCaseRequest> {

	private final InItDao dao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper itTimeDetailsMapper;
	private final InItEntityConverter entityConverter;

	private final ItQueryManager itQueryManager;
	private final WithDao withDao;

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
			final GetInItId inItId = source::getId;
			ItTimeDetails timeInfo = extractTimeInfo(source);
			BasicIt it = itQueryManager.query(IT_REGISTERED, inItId);
			Long withCount = withDao.countEndByInIt(inItId.getId());
			endItInfos.add(makeEndItInfo(source, timeInfo, it, withCount));
		}
		return buildResponse(endItInfos);
	}

	private List<InIt> getSources(GetMemberId memberId) {
		List<InItEntity> entities = new ArrayList<>(dao.findAllEndStatusByMember(memberId.getId()));
		List<InIt> sources = new ArrayList<>();
		for (InItEntity source : entities) {
			ItRelationEntity itRelation =
					itRelationDao.findByInItIdAndStatus(source.getId(), ItStatus.END).orElse(null);
			assert itRelation != null;
			String info = source.getInfo();
			InItTimeDetails timeInfo = itTimeDetailsMapper.read(info, InItTimeDetails.class);
			sources.add(entityConverter.from(source, itRelation, timeInfo));
		}
		return sources;
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

	private EndItInfo makeEndItInfo(InIt source, ItTimeDetails timeInfo, BasicIt it, Long withCount) {
		return EndItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.topic(it.getTopic())
				.startTime(timeInfo.getStartTime())
				.endTime(timeInfo.getEndTime())
				.startDate(source.getCreateAt().toLocalDate())
				.endDate(source.getUpdateAt().toLocalDate())
				.withCount(withCount)
				.build();
	}
}
