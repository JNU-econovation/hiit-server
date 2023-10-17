package com.hiit.api.domain.support;

import com.hiit.api.common.marker.dto.AbstractDto;
import com.hiit.api.common.marker.entity.EntityMarker;
import com.hiit.api.common.support.converter.AbstractConverter;

/**
 * 엔티티를 도메인으로 변환하기 위한 서비스 계층 컨버터 인터페이스
 *
 * @param <T> 엔티티 타입
 * @param <R> 도메인 타입
 * @param <K> 컨트롤러 계층에 전달할 Dto 타입
 */
public interface AbstractDomainConverter<T extends EntityMarker, R, K extends AbstractDto>
		extends AbstractConverter<T, R> {

	/**
	 * 엔티티 타입을 도메인으로 변환한다
	 *
	 * @param t DTO 타입의 객체
	 * @return 도메인
	 */
	@Override
	R from(T t);

	/**
	 * 도메인을 Dto로 변환한다.
	 *
	 * @param r 도메인 객체
	 * @return Dto 타입
	 */
	K to(R r);
}
