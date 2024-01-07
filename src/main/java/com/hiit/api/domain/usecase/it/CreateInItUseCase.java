package com.hiit.api.domain.usecase.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.it.CreateInItUseCaseRequest;
import com.hiit.api.domain.exception.BadRequestException;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.it.BrowseTargetItServiceManager;
import com.hiit.api.domain.service.it.SaveItRelationService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.member.MemberEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateInItUseCase implements AbstractUseCase<CreateInItUseCaseRequest> {

	private final InItDao inItDao;

	private final MemberDao memberDao;
	private final MemberEntityConverter memberEntityConverter;

	private final SaveItRelationService saveItRelationService;
	private final BrowseTargetItServiceManager browseTargetItServiceManager;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(CreateInItUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long itId = request.getItId();
		final TargetItTypeInfo type = request.getType();
		final String dayCode = request.getDayCode();
		final String resolution = request.getResolution();

		log.debug("browse in it ids : m - {}", memberId);
		List<Long> inItIds = browseInItIds(memberId);
		if (inItIds.contains(itId)) {
			// 이미 참여 중인 it
			log.debug("already in it : m - {}, it - {}", memberId, itId);
			return AbstractResponse.VOID;
		}

		log.debug("read member : m - {}", memberId);
		Member member = readMember(memberId);
		if (!member.isSameId(memberId)) {
			throw new BadRequestException();
		}

		String topic = readItTopic(type, itId);

		// 현재 제목을 작성하는 화면이 없다, 그래서 임시로 topic을 title로 사용
		InItEntity entity =
				InItEntity.builder()
						.title(topic)
						.resolution(resolution)
						.dayCode(DayCodeList.valueOf(dayCode))
						.hiitMember(HiitMemberEntity.builder().id(memberId).build())
						.build();
		InItEntity inIt = inItDao.save(entity);
		saveItRelationService.execute(itId, inIt);
		return AbstractResponse.VOID;
	}

	private List<Long> browseInItIds(Long memberId) {
		return inItDao.findAllActiveStatusByMember(memberId).stream()
				.map(InItEntity::getItRelationEntity)
				.map(ItRelationEntity::getTargetItId)
				.distinct()
				.collect(Collectors.toList());
	}

	private Member readMember(Long memberId) {
		Optional<HiitMemberEntity> source = memberDao.findById(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return memberEntityConverter.from(source.get());
	}

	private String readItTopic(TargetItTypeInfo type, Long itId) {
		BasicIt source = browseTargetItServiceManager.browse(TargetItTypeInfo.of(type.getType()), itId);
		return source.getTopic();
	}
}
