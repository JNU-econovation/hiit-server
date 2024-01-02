package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.model.it.registered.RegisteredIt;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;

public interface RegisteredItEntityConverter
		extends AbstractEntityConverter<RegisteredItEntity, RegisteredIt> {}
