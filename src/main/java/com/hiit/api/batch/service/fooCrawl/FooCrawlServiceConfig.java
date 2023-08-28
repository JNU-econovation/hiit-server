package com.hiit.api.batch.service.fooCrawl;

import com.hiit.api.batch.service.fooCrawl.step.complete.FooCrawlListWriter;
import com.hiit.api.batch.service.fooCrawl.step.complete.FooCrawlProcessor;
import com.hiit.api.batch.service.fooCrawl.step.complete.FooCrawlReader;
import com.hiit.api.batch.service.fooCrawl.step.fail.FooUnCompleteCrawlListWriter;
import com.hiit.api.batch.service.fooCrawl.step.fail.FooUnCompleteCrawlReader;
import com.hiit.api.batch.support.listener.ChunkLoggingListener;
import com.hiit.api.batch.support.listener.JobLoggingListener;
import com.hiit.api.batch.support.listener.StepLoggingListener;
import com.hiit.api.batch.support.param.TimeStamper;
import com.hiit.api.batch.support.policy.SizeAndTimeCompletionPolicyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FooCrawlServiceConfig {
	public static final String JOB_NAME = "fooCrawlJob";
	private static final int CHUNK_SIZE = 4;
	private static final long TIMEOUT = 100L;

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final JobLoggingListener jobLoggingListener;
	private final StepLoggingListener stepLoggingListener;
	private final ChunkLoggingListener chunkLoggingListener;
	private final TimeStamper timeStamper;

	private final FooCrawlReader fooCrawlReader;
	private final FooCrawlProcessor fooCrawlProcessor;
	private final FooCrawlListWriter fooCrawlListWriter;

	private final FooUnCompleteCrawlReader fooUnCompleteCrawlReader;
	private final FooUnCompleteCrawlListWriter fooUnCompleteCrawlListWriter;

	@Bean(name = JOB_NAME)
	public Job fooCrawlJob() {
		return this.jobBuilderFactory
				.get(JOB_NAME)
				.incrementer(timeStamper)
				.start(this.fooCrawlStep())
				.on(ExitStatus.FAILED.getExitCode())
				.to(this.fooCrawlFailStep())
				.on("*")
				.end()
				.from(this.fooCrawlStep())
				.on("*")
				.end()
				.end()
				.listener(jobLoggingListener)
				.build();
	}

	@Bean
	public Step fooCrawlStep() {
		return this.stepBuilderFactory
				.get("fooCrawlStep")
				.<String, String>chunk(SizeAndTimeCompletionPolicyFactory.get(CHUNK_SIZE, TIMEOUT))
				.reader(fooCrawlReader)
				.processor(fooCrawlProcessor)
				.writer(fooCrawlListWriter)
				.listener(stepLoggingListener)
				.listener(chunkLoggingListener)
				.build();
	}

	@Bean
	public Step fooCrawlFailStep() {
		return this.stepBuilderFactory
				.get("fooCrawlFailStep")
				.<String, String>chunk(SizeAndTimeCompletionPolicyFactory.get(CHUNK_SIZE, TIMEOUT))
				.reader(fooUnCompleteCrawlReader)
				.writer(fooUnCompleteCrawlListWriter)
				.listener(stepLoggingListener)
				.listener(chunkLoggingListener)
				.build();
	}
}
