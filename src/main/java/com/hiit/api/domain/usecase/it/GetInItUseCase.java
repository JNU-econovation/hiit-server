package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.GetInItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
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
public class GetInItUseCase implements AbstractUseCase<GetInItUseCaseRequest> {

	private final InItDao dao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper itTimeDetailsMapper;
	private final InItEntityConverter itEntityConverter;

	private final ItQueryManager itQueryManager;
	private final ItActiveMemberCountService itActiveMemberCountService;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public InItInfo execute(GetInItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getInIt;

		InIt source = getSource(inItId, memberId);
		if (!source.isOwner(memberId)) {
			throw new MemberAccessDeniedException(memberId.getId(), inItId.getId());
		}

		ItTypeDetails type = ItTypeDetails.of(source.getItType());
		Long activeMemberCount = itActiveMemberCountService.execute(source::getItId);
		BasicIt it = itQueryManager.query(type, (GetInItId) source::getId);
		String topic = it.getTopic();
		return buildResponse(source, type.getValue(), topic, activeMemberCount);
	}

	private InIt getSource(GetInItId inItId, GetMemberId memberId) {
		Optional<InItEntity> source =
				dao.findActiveStatusByIdAndMember(inItId.getId(), memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.key, inItId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, GetMemberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		ItRelationEntity itRelation =
				itRelationDao.findByInItIdAndStatus(inIt.getId(), ItStatus.ACTIVE).orElse(null);
		assert itRelation != null;
		String info = inIt.getInfo();
		InItTimeDetails timeDetails = itTimeDetailsMapper.read(info, InItTimeDetails.class);
		return itEntityConverter.from(inIt, itRelation, timeDetails);
	}

	private InItInfo buildResponse(InIt source, String type, String topic, Long inMemberCount) {
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
