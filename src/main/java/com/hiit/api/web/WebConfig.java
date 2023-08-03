package com.hiit.api.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** Web 설정 클래스 */
@Configuration
@ComponentScan(basePackages = WebConfig.BASE_PACKAGE)
public class WebConfig {
	public static final String BASE_PACKAGE = "com.hiit.api.web";
	public static final String SERVICE_NAME = "hiit";
	public static final String MODULE_NAME = "web";
	public static final String BEAN_NAME_PREFIX = SERVICE_NAME + MODULE_NAME;
	public static final String PROPERTY_PREFIX = SERVICE_NAME + "." + MODULE_NAME;
}
