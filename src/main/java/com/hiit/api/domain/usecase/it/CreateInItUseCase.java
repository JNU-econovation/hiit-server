package com.hiit.api.domain.usecase.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.it.CreateInItUseCaseRequest;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ItQueryManager;
import com.hiit.api.domain.service.it.ItRelationCommand;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateInItUseCase implements AbstractUseCase<CreateInItUseCaseRequest> {

	private final InItDao dao;

	private final MemberQuery memberQuery;
	private final ItQueryManager itQueryManager;
	private final ItRelationCommand itRelationCommand;

	@Override
	@Transactional
	public AbstractResponse execute(CreateInItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetItId itId = request::getItId;
		final ItTypeDetails type = request.getType();
		final String dayCode = request.getDayCode();
		final String resolution = request.getResolution();

		GetMemberId member = memberQuery.query(memberId);
		List<Long> inItIds = browseInItIds(member.getId());
		if (inItIds.contains(itId.getId())) {
			// 이미 참여 중인 it
			return AbstractResponse.VOID;
		}

		BasicIt it = itQueryManager.query(ItTypeDetails.of(type.getValue()), itId);
		String topic = it.getTopic();

		// 현재 제목을 작성하는 화면이 없다, 그래서 임시로 topic을 title로 사용
		InItEntity entity =
				InItEntity.builder()
						.title(topic)
						.resolution(resolution)
						.dayCode(DayCodeList.valueOf(dayCode))
						.hiitMember(HiitMemberEntity.builder().id(member.getId()).build())
						.build();
		save(entity, itId);
		return AbstractResponse.VOID;
	}

	private List<Long> browseInItIds(Long memberId) {
		return dao.findAllActiveStatusByMember(memberId).stream()
				.map(InItEntity::getItRelationEntity)
				.map(ItRelationEntity::getTargetItId)
				.distinct()
				.collect(Collectors.toList());
	}

	private void save(InItEntity entity, GetItId itId) {
		InItEntity inIt = dao.save(entity);
		itRelationCommand.save(itId, inIt);
	}
}
