package com.hiit.api.domain.usecase.hit;

import com.hiit.api.domain.model.hit.Hit;
import com.hiit.api.domain.support.AbstractEntityConverter;
import com.hiit.api.repository.entity.business.hit.HitEntity;

public interface HitEntityConverter extends AbstractEntityConverter<HitEntity, Hit> {}
