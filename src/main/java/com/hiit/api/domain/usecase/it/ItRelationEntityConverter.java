package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;

public interface ItRelationEntityConverter
		extends AbstractEntityConverter<ItRelationEntity, ItRelation> {}
