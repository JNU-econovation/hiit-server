package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.end.GetEndItUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.end.EndItTimeDetails;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.service.it.ItTypeQueryManager;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndItUseCase implements AbstractUseCase<GetEndItUseCaseRequest> {

	private final InItDao dao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper timeDetailsMapper;
	private final InItEntityConverter entityConverter;

	private final ItTypeQueryManager itTypeQueryManager;
	private final WithDao withDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public EndItInfo execute(final GetEndItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId endInItId = request::getEndInItId;

		InIt source = getSource(memberId, endInItId);
		if (!source.isOwner(memberId)) {
			throw new MemberAccessDeniedException(memberId.getId(), endInItId.getId());
		}

		ItTimeDetails timeInfo = extractTimeInfo(source);
		BasicIt it = itTypeQueryManager.query(ItTypeDetails.IT_REGISTERED, endInItId);
		Long withCount = withDao.countEndByInIt(source.getId());
		return buildResponse(source, timeInfo, it, withCount);
	}

	private InIt getSource(GetMemberId memberId, GetInItId endInItId) {
		Optional<InItEntity> source =
				dao.findEndStatusByIdAndMemberId(endInItId.getId(), memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.endKey, endInItId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, GetMemberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		ItRelationEntity itRelation =
				itRelationDao.findByInItIdAndStatus(inIt.getId(), ItStatus.END).orElse(null);
		assert itRelation != null;
		String info = inIt.getInfo();
		ItTimeDetails timeInfo = timeDetailsMapper.read(info, InItTimeDetails.class);
		return entityConverter.from(inIt, itRelation, timeInfo);
	}

	private EndItInfo buildResponse(InIt source, ItTimeDetails timeInfo, BasicIt it, Long withCount) {
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

	private ItTimeDetails extractTimeInfo(InIt inIt) {
		ItTimeDetails source = inIt.getTime();
		return EndItTimeDetails.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}
}
