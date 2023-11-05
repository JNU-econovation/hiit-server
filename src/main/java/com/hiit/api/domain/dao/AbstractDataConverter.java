package com.hiit.api.domain.dao;

import com.hiit.api.common.marker.dto.AbstractData;
import com.hiit.api.common.marker.entity.EntityMarker;
import com.hiit.api.common.support.converter.AbstractConverter;

public interface AbstractDataConverter<E extends EntityMarker, D extends AbstractData>
		extends AbstractConverter<E, D> {

	@Override
	D from(E t);

	E to(D d);
}
