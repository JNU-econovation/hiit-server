package com.hiit.api.domain.usecase.with;

import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.domain.support.AbstractTimeInfoEntityConverter;
import com.hiit.api.repository.entity.business.with.WithEntity;

public interface WithEntityConverter
		extends AbstractEntityConverter<WithEntity, With>,
				AbstractTimeInfoEntityConverter<WithEntity, With> {}
