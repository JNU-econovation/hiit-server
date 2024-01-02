package com.hiit.api.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 시큐리티 설정 클래스 */
@Configuration
@ComponentScan(basePackages = SecurityConfig.BASE_PACKAGE)
public class SecurityConfig {

	public static final String BASE_PACKAGE = "com.hiit.api.security";
	public static final String SERVICE_NAME = "hiit";
	public static final String MODULE_NAME = "security";
	public static final String BEAN_NAME_PREFIX = SERVICE_NAME + MODULE_NAME;
	public static final String PROPERTY_PREFIX = SERVICE_NAME + "." + MODULE_NAME;
}
