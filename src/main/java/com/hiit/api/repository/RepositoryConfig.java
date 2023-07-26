package com.hiit.api.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 저장소 설정 클래스 */
@Configuration
@ComponentScan(basePackages = RepositoryConfig.BASE_PACKAGE)
@EnableAutoConfiguration(
		exclude = {
			DataSourceAutoConfiguration.class,
			DataSourceTransactionManagerAutoConfiguration.class,
			HibernateJpaAutoConfiguration.class,
		})
public class RepositoryConfig {

	public static final String BASE_PACKAGE = "com.hiit.api.repository";
	public static final String SERVICE_NAME = "hiit";
	public static final String MODULE_NAME = "repository";
	public static final String BEAN_NAME_PREFIX = SERVICE_NAME + MODULE_NAME;
	public static final String PROPERTY_PREFIX = SERVICE_NAME + "." + MODULE_NAME;
}
