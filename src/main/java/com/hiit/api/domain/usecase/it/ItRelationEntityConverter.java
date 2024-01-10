package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;

public interface ItRelationEntityConverter
		extends AbstractEntityConverter<ItRelationEntity, It_Relation> {}
