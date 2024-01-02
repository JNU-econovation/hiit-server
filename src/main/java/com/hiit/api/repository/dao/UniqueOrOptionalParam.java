package com.hiit.api.repository.dao;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** 단일 조회 결과를 나오게 하는 파라미터 표시 마커 인터페이스 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface UniqueOrOptionalParam {}
