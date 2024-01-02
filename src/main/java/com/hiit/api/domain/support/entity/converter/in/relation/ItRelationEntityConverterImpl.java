package com.hiit.api.domain.support.entity.converter.in.relation;

import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.TargetItType;
import org.springframework.stereotype.Component;

@Component
public class ItRelationEntityConverterImpl
		implements AbstractEntityConverter<ItRelationEntity, ItRelation> {

	@Override
	public ItRelation from(ItRelationEntity entity) {
		return ItRelation.builder()
				.id(entity.getId())
				.inItId(entity.getInIt().getId())
				.targetItId(entity.getTargetItId())
				.targetItType(TargetItTypeInfo.of(entity.getTargetItType().getType()))
				.build();
	}

	@Override
	public ItRelationEntity to(ItRelation data) {
		return ItRelationEntity.builder()
				.targetItId(data.getTargetItId())
				.targetItType(TargetItType.of(data.getTargetItType().getType()))
				.inIt(InItEntity.builder().id(data.getInItId()).build())
				.build();
	}

	@Override
	public ItRelationEntity to(Long id, ItRelation data) {
		return to(data).toBuilder().id(id).build();
	}
}
