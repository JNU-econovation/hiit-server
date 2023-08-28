package com.hiit.api.batch.config;

import static com.hiit.api.batch.BatchConfig.BEAN_NAME_PREFIX;
import static com.hiit.api.batch.config.BatchDataSourceConfig.DATASOURCE_NAME;
import static com.hiit.api.batch.config.BatchSupportConfig.PROPERTY_BEAN_NAME;
import static com.hiit.api.batch.config.DelegatedBatchConfigurer.DELEGATED_BATCH_CONFIGURER_BEAN_NAME;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** DB에 저장되는 Batch 메타 정보와 관련된 설정 클래스 */
@Configuration
public class BatchMetaDataConfig {

	public static final String JOB_REGISTRY_BEAN_NAME = BEAN_NAME_PREFIX + "JobRegistry";

	public static final String JOB_REPOSITORY_BEAN_NAME = BEAN_NAME_PREFIX + "JobRepository";
	public static final String JOB_EXPLORER_BEAN_NAME = BEAN_NAME_PREFIX + "JobExplorer";
	private static final String BATCH_DATA_SOURCE_SCRIPT_DATABASE_INITIALIZER_BEAN_NAME =
			BEAN_NAME_PREFIX + "BatchDataSourceScriptDatabaseInitializer";

	/**
	 * JobRegistry Bean을 생성한다.
	 *
	 * @return JobRegistry 객체
	 */
	@Bean(name = JOB_REGISTRY_BEAN_NAME)
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();
	}

	/**
	 * JobRepository Bean을 생성한다.
	 *
	 * @param configurer BatchConfigurer 객체
	 * @return JobRepository 객체
	 */
	@Bean(name = JOB_REPOSITORY_BEAN_NAME)
	public JobRepository jobRepository(
			@Qualifier(value = DELEGATED_BATCH_CONFIGURER_BEAN_NAME) BatchConfigurer configurer)
			throws Exception {
		return configurer.getJobRepository();
	}

	/**
	 * JobExplorer Bean을 생성한다.
	 *
	 * @param configurer BatchConfigurer 객체
	 * @return JobExplorer 객체
	 */
	@Bean(name = JOB_EXPLORER_BEAN_NAME)
	public JobExplorer jobExplorer(
			@Qualifier(value = DELEGATED_BATCH_CONFIGURER_BEAN_NAME) BatchConfigurer configurer)
			throws Exception {
		return configurer.getJobExplorer();
	}

	/**
	 * BatchDataSourceScriptDatabaseInitializer Bean을 생성한다. <br>
	 * 단 Local 환경에서만 동작한다. Local 환경 외에서는 Flyway를 통해 DB 스키마를 관리한다.
	 *
	 * @param dataSource DataSource 객체
	 * @param properties BatchProperties 객체
	 * @return BatchDataSourceScriptDatabaseInitializer 객체
	 */
	@Profile(value = "local")
	@Bean(name = BATCH_DATA_SOURCE_SCRIPT_DATABASE_INITIALIZER_BEAN_NAME)
	BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(
			@Qualifier(value = DATASOURCE_NAME) DataSource dataSource,
			@Qualifier(value = PROPERTY_BEAN_NAME) BatchProperties properties) {
		return new BatchDataSourceScriptDatabaseInitializer(dataSource, properties.getJdbc());
	}
}
