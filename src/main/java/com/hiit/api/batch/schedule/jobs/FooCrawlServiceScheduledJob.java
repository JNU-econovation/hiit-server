package com.hiit.api.batch.schedule.jobs;

import com.hiit.api.batch.service.fooCrawl.FooCrawlServiceConfig;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Configuration
public class FooCrawlServiceScheduledJob extends QuartzJobBean {

	private final Job job;
	private final JobExplorer jobExplorer;
	private final JobLauncher jobLauncher;

	public FooCrawlServiceScheduledJob(
			@Qualifier(value = FooCrawlServiceConfig.JOB_NAME) Job job,
			JobExplorer jobExplorer,
			JobLauncher jobLauncher) {
		this.job = job;
		this.jobExplorer = jobExplorer;
		this.jobLauncher = jobLauncher;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {
		JobParameters jobParameters =
				new JobParametersBuilder(this.jobExplorer).getNextJobParameters(job).toJobParameters();
		try {
			this.jobLauncher.run(this.job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
