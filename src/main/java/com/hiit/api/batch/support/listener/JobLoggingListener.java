package com.hiit.api.batch.support.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/** Job 리스너 */
@Slf4j
@Component
public class JobLoggingListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {}

	@Override
	public void afterJob(JobExecution jobExecution) {}
}
