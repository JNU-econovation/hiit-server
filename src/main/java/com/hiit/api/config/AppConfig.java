package com.hiit.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** App 설정 클래스 */
@Configuration
@ComponentScan(basePackages = AppConfig.BASE_PACKAGE)
public class AppConfig {

	public static final String BASE_PACKAGE = "com.hiit.api";
	public static final String SERVICE_NAME = "hiit";
	public static final String MODULE_NAME = "app";
	public static final String BEAN_NAME_PREFIX = SERVICE_NAME + MODULE_NAME;
	public static final String PROPERTY_PREFIX = SERVICE_NAME + "." + MODULE_NAME;
}
