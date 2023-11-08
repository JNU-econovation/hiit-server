package com.hiit.api.domain.dao;

import com.hiit.api.common.marker.dto.AbstractData;
import com.hiit.api.common.marker.entity.EntityMarker;
import com.hiit.api.common.support.converter.AbstractConverter;

/**
 * 레포지토리 계층의 엔티티를 도메인 계층의 데이터로 변환하는 컨버터 인터페이스
 *
 * @param <E> 엔티티 타입
 * @param <D> 데이터 타입
 */
public interface AbstractDataConverter<E extends EntityMarker, D extends AbstractData>
		extends AbstractConverter<E, D> {

	/**
	 * 엔티티 타입을 데이터 타입으로 변환한다
	 *
	 * @param e 엔티티 타입
	 * @return 데이터 타입
	 */
	@Override
	D from(E e);

	/**
	 * 데이터 타입을 엔티티 타입으로 변환한다
	 *
	 * @param d 데이터 타입
	 * @return 엔티티 타입
	 */
	E to(D d);
}
