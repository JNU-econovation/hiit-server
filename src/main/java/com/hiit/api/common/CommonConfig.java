package com.hiit.api.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 공통 클래스 설정 클래스 */
@Configuration
@ComponentScan(basePackages = CommonConfig.BASE_PACKAGE)
public class CommonConfig {

	public static final String BASE_PACKAGE = "com.hiit.api.common";
	public static final String SERVICE_NAME = "hiit";
	public static final String MODULE_NAME = "common";
	public static final String BEAN_NAME_PREFIX = SERVICE_NAME + MODULE_NAME;
	public static final String PROPERTY_PREFIX = SERVICE_NAME + "." + MODULE_NAME;
}
