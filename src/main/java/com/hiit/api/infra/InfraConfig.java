package com.hiit.api.infra;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 인프라 설정 클래스 */
@Configuration
@ComponentScan(basePackages = InfraConfig.BASE_PACKAGE)
public class InfraConfig {

	public static final String BASE_PACKAGE = "com.hiit.api.infra";
	public static final String SERVICE_NAME = "hiit";
	public static final String MODULE_NAME = "infra";
	public static final String BEAN_NAME_PREFIX = SERVICE_NAME + MODULE_NAME;
	public static final String PROPERTY_PREFIX = SERVICE_NAME + "." + MODULE_NAME;
}
