package com.hiit.api.batch.config;

import static com.hiit.api.batch.BatchConfig.BEAN_NAME_PREFIX;
import static com.hiit.api.batch.config.DelegatedBatchConfigurer.DELEGATED_BATCH_CONFIGURER_BEAN_NAME;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Batch 실행 설정 클래스 */
@Configuration
public class BatchLaunchConfig {

	public static final String JOB_LAUNCHER_BEAN_NAME = BEAN_NAME_PREFIX + "JobLauncher";

	/**
	 * JobLauncher Bean을 생성한다.
	 *
	 * @param configurer BatchConfigurer 객체
	 * @return JobLauncher 객체
	 */
	@Bean(name = JOB_LAUNCHER_BEAN_NAME)
	public JobLauncher jobLauncher(
			@Qualifier(value = DELEGATED_BATCH_CONFIGURER_BEAN_NAME) BatchConfigurer configurer)
			throws Exception {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(configurer.getJobRepository());
		return simpleJobLauncher;
	}
}
