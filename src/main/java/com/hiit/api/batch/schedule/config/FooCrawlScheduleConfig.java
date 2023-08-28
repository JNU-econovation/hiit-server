package com.hiit.api.batch.schedule.config;

import com.hiit.api.batch.BatchConfig;
import com.hiit.api.batch.schedule.jobs.FooCrawlServiceScheduledJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.spi.MutableTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooCrawlScheduleConfig {

	private final String REPEAT_10_SECONDS = "0/10 * * * * ?";

	@Bean(name = BatchConfig.BEAN_NAME_PREFIX + "FooCrawlJobDetail")
	public JobDetail quartzJobDetail() {
		return JobBuilder.newJob(FooCrawlServiceScheduledJob.class).storeDurably().build();
	}

	@Bean(name = BatchConfig.BEAN_NAME_PREFIX + "FooCrawlJobTrigger")
	public Trigger jobTrigger() {
		MutableTrigger cron = CronScheduleBuilder.cronSchedule(REPEAT_10_SECONDS).build();

		return TriggerBuilder.newTrigger()
				.forJob(quartzJobDetail())
				.withSchedule(cron.getScheduleBuilder())
				.build();
	}
}
