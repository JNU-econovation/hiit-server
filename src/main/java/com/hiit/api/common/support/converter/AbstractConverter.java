package com.hiit.api.common.support.converter;

/**
 * T -> R로 변환하는 컨버터 인터페이스
 *
 * @param <T> 변환하려는 타입
 * @param <R> 변환된 타입
 */
@FunctionalInterface
public interface AbstractConverter<T, R> {

	R from(T t);
}
