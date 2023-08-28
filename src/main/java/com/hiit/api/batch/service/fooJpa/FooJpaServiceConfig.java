package com.hiit.api.batch.service.fooJpa;

import com.hiit.api.batch.support.listener.ChunkLoggingListener;
import com.hiit.api.batch.support.listener.JobLoggingListener;
import com.hiit.api.batch.support.listener.StepLoggingListener;
import com.hiit.api.batch.support.param.TimeStamper;
import com.hiit.api.repository.config.EntityJpaDataSourceConfig;
import com.hiit.api.repository.entity.business.FooEntity;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FooJpaServiceConfig {
	public static final String JOB_NAME = "fooJpaJob";
	private static final int CHUNK_SIZE = 5;

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final JobLoggingListener jobLoggingListener;
	private final StepLoggingListener stepLoggingListener;
	private final ChunkLoggingListener chunkLoggingListener;
	private final TimeStamper timeStamper;

	private EntityManagerFactory entityManagerFactory;
	private PlatformTransactionManager transactionManager;

	@Autowired
	public void setEntityManagerFactory(
			@Qualifier(value = EntityJpaDataSourceConfig.ENTITY_MANAGER_FACTORY_NAME)
					EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Autowired
	public void setTransactionManager(
			@Qualifier(value = EntityJpaDataSourceConfig.TRANSACTION_MANAGER_NAME)
					PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Bean(name = JOB_NAME)
	public Job fooJpaJob() {
		return this.jobBuilderFactory
				.get(JOB_NAME)
				.incrementer(timeStamper)
				.start(this.fooJpaStep())
				.listener(jobLoggingListener)
				.build();
	}

	@Bean
	public Step fooJpaStep() {
		return this.stepBuilderFactory
				.get("fooJpaStep")
				.<FooEntity, FooEntity>chunk(CHUNK_SIZE)
				.reader(fooJpaReader())
				.processor(fooJpaProcessor())
				.writer(fooJpaWriter())
				.transactionManager(transactionManager)
				.listener(stepLoggingListener)
				.listener(chunkLoggingListener)
				.build();
	}

	@Bean
	public JpaPagingItemReader<FooEntity> fooJpaReader() {
		return new JpaPagingItemReaderBuilder<FooEntity>()
				.name("fooJpaReader")
				.entityManagerFactory(entityManagerFactory)
				.pageSize(CHUNK_SIZE)
				.queryString("SELECT f FROM foo f")
				.build();
	}

	@Bean
	public ItemProcessor<FooEntity, FooEntity> fooJpaProcessor() {
		return item -> {
			log.info("on processor : {}", item);
			String name = item.getName();
			return item.toBuilder().name(name + "_").build();
		};
	}

	@Bean
	public JpaItemWriter<FooEntity> fooJpaWriter() {
		JpaItemWriter<FooEntity> fooEntityJpaItemWriter = new JpaItemWriter<>();
		fooEntityJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
		return fooEntityJpaItemWriter;
	}
}
