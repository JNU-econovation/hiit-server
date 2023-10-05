package com.hiit.api.common.support.converter;

public interface AbstractConverter<T, R> {

	R from(T t);
}
