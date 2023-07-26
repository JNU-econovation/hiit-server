package com.hiit.api.common.support.converter;

import com.hiit.api.common.marker.dto.AbstractDto;

/**
 * DTO를 원하는 값으로 변환하기 위한 DtoConverter 인터페이스
 *
 * @param <T> AbstractDto를 상속받은 DTO를 사용합니다.
 * @param <R> 변환하고자 하는 값의 타입을 사용합니다.
 */
public interface AbstractDtoConverter<T extends AbstractDto, R> {

	/**
	 * DTO 타입을 원하는 타입으로 변환한다.<br>
	 * 이 구현은 DTO 타입의 객체를 파라미터로 받아 변환하고자 하는 타입의 객체를 반환합니다.
	 *
	 * @param t DTO 타입의 객체
	 * @return 변환된 값
	 */
	R from(T t);

	/**
	 * to의 경우 R을 파라미터로 받고 리턴을 다양하게 할 가능성이 크다.<br>
	 * 그렇기에 인터페이스로 선언하지 않아고 리턴할 타입에 따라 메서드를 선언한다.<br>
	 * ex) toDomainResponse, toEntityDto 등등
	 */
}
