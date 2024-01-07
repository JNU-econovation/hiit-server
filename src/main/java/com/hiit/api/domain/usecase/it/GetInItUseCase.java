package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.GetInItUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.service.it.BrowseTargetItServiceManager;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
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

	private final InItDao inItDao;
	private final InItEntityConverter inItEntityConverter;

	private final ItRelationDao itRelationDao;
	private final ItRelationEntityConverter itRelationEntityConverter;

	private final BrowseTargetItServiceManager browseTargetItServiceManager;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public InItInfo execute(GetInItUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long inItId = request.getInIt();

		InIt source = getSource(inItId, memberId);
		if (!source.isOwner(memberId)) {
			log.debug("{} is not owner of {}", memberId, inItId);
			throw new MemberAccessDeniedException(memberId, inItId);
		}

		ItRelation itRelation = readRelation(source);
		String type = itRelation.getTargetItType().getType();
		Long inMemberCount = calcMemberCount(itRelationEntityConverter.to(itRelation));
		String topic = readTopic(itRelation);
		return buildResponse(source, type, topic, inMemberCount);
	}

	private InIt getSource(Long inItId, Long memberId) {
		return inItEntityConverter.from(inItDao.findActiveStatusByIdAndMember(inItId, memberId));
	}

	private InItInfo buildResponse(InIt source, String type, String topic, Long inMemberCount) {
		return InItInfo.builder()
				.id(source.getId())
				.title(source.getTitle())
				.topic(topic)
				.startTime(source.getTimeInfo().getStartTime())
				.endTime(source.getTimeInfo().getEndTime())
				.days(source.getDayCode().getDays())
				.inMemberCount(inMemberCount)
				.type(type)
				.build();
	}

	private ItRelation readRelation(InIt source) {
		Optional<ItRelationEntity> relation = itRelationDao.findById(source.getItRelationId());
		if (relation.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate("relationId", source.getItRelationId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return itRelationEntityConverter.from(relation.get());
	}

	private Long calcMemberCount(ItRelationEntity itRelation) {
		return itRelationDao.countByTargetItId(itRelation.getTargetItId());
	}

	private String readTopic(ItRelation itRelation) {
		TargetItTypeInfo type = itRelation.getTargetItType();
		Long inItId = itRelation.getInItId();
		BasicIt source = browseTargetItServiceManager.browse(type, inItId);
		return source.getTopic();
	}
}
