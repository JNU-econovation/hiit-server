package com.hiit.api.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** domain 설정 클래스 */
@Configuration
@ComponentScan(basePackages = DomainConfig.BASE_PACKAGE)
public class DomainConfig {
	public static final String BASE_PACKAGE = "com.hiit.api.domain";
}
