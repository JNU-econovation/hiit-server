package com.hiit.api.batch.config;

import static com.hiit.api.batch.BatchConfig.BEAN_NAME_PREFIX;
import static com.hiit.api.batch.BatchConfig.PROPERTY_PREFIX;

import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Batch 지원 설정 클래스 */
@Configuration
public class BatchSupportConfig {

	public static final String PROPERTY_BEAN_NAME = BEAN_NAME_PREFIX + "Properties";

	/**
	 * BatchProperties Bean을 생성한다.
	 *
	 * @return BatchProperties 객체
	 */
	@Bean(name = PROPERTY_BEAN_NAME)
	@ConfigurationProperties(prefix = PROPERTY_PREFIX)
	public BatchProperties batchProperties() {
		return new BatchProperties();
	}
}
