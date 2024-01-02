package com.hiit.api.domain.usecase.it;

import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.domain.support.AbstractTimeInfoEntityConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;

public interface InItEntityConverter
		extends AbstractEntityConverter<InItEntity, InIt>,
				AbstractTimeInfoEntityConverter<InItEntity, InIt> {}
