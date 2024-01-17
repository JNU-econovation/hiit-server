package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.model.ItTimeDetails;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;

public interface InItEntityConverter {

	InIt from(InItEntity entity, ItRelationEntity relation, ItTimeDetails timeInfo);

	InItEntity to(Long id, InIt data);
}
