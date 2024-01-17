package com.hiit.api.domain.support.entity.converter.in.relation;

import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.usecase.it.ItRelationEntityConverter;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItType;
import org.springframework.stereotype.Component;

@Component
public class ItRelationEntityConverterImpl implements ItRelationEntityConverter {

	@Override
	public It_Relation from(ItRelationEntity entity) {
		return It_Relation.builder()
				.id(entity.getId())
				.inItId(entity.getInItId())
				.itId(entity.getItId())
				.type(ItTypeDetails.of(entity.getItType().getType()))
				.build();
	}

	@Override
	public ItRelationEntity to(It_Relation data) {
		return ItRelationEntity.builder()
				.itId(data.getItId())
				.itType(ItType.of(data.getType().getValue()))
				.inItId(data.getInItId())
				.build();
	}

	@Override
	public ItRelationEntity to(Long id, It_Relation data) {
		return to(data).toBuilder().id(id).build();
	}
}
