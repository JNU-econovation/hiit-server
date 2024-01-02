package com.hiit.api.domain.support;

import com.hiit.api.common.marker.entity.EntityMarker;
import com.hiit.api.common.marker.model.AbstractDomain;

/** 엔티티와 도메인을 변환하는 인터페이스 */
public interface AbstractEntityConverter<T extends EntityMarker, R extends AbstractDomain> {

	/** 엔티티를 도메인으로 변환 */
	R from(T t);

	/** 도메인을 엔티티로 변환 */
	T to(R r);

	/** 아이디가 포함된 도메인을 엔티티로 변환 */
	T to(Long id, R r);
}
