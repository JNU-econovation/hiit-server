package com.hiit.api.domain.support.entity.converter.in.relation;

import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.usecase.it.ItRelationEntityConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.TargetItType;
import org.springframework.stereotype.Component;

@Component
public class ItRelationEntityConverterImpl implements ItRelationEntityConverter {

	@Override
	public It_Relation from(ItRelationEntity entity) {
		return It_Relation.builder()
				.id(entity.getId())
				.inItId(entity.getInIt().getId())
				.itId(entity.getTargetItId())
				.type(ItTypeDetails.of(entity.getTargetItType().getType()))
				.build();
	}

	@Override
	public ItRelationEntity to(It_Relation data) {
		return ItRelationEntity.builder()
				.targetItId(data.getItId())
				.targetItType(TargetItType.of(data.getType().getValue()))
				.inIt(InItEntity.builder().id(data.getInItId()).build())
				.build();
	}

	@Override
	public ItRelationEntity to(Long id, It_Relation data) {
		return to(data).toBuilder().id(id).build();
	}
}
