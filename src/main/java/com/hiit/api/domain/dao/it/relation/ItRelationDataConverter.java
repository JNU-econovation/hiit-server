package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.TargetItType;
import org.springframework.stereotype.Component;

@Component
public class ItRelationDataConverter
		implements AbstractDataConverter<ItRelationEntity, ItRelationData> {

	@Override
	public ItRelationData from(ItRelationEntity entity) {
		return ItRelationData.builder()
				.targetId(entity.getTargetItId())
				.targetType(TargetItTypeInfo.valueOf(entity.getTargetItType().name()))
				.inItId(entity.getInIt().getId())
				.build();
	}

	public ItRelationEntity to(ItRelationData data) {
		return ItRelationEntity.builder()
				.targetItId(data.getTargetId())
				.targetItType(TargetItType.valueOf(data.getTargetType().name()))
				.inIt(InItEntity.builder().id(data.getInItId()).build())
				.build();
	}
}
