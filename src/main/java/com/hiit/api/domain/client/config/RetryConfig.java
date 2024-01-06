package com.hiit.api.domain.client.config;

import com.hiit.api.domain.client.listener.KaKaoClientListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class RetryConfig {

	private final KaKaoClientListener kaKaoClientListener;

	@Bean
	public RetryTemplate kakaoRetryTemplate(
			@Value("${kakao.retry.maxAttempts}") int maxAttempts,
			@Value("${kakao.retry.backOffPeriod}") int backOffPeriod) {
		RetryTemplate retryTemplate = new RetryTemplate();

		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(backOffPeriod);
		retryTemplate.setBackOffPolicy(backOffPolicy);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(maxAttempts);
		retryTemplate.setRetryPolicy(retryPolicy);

		retryTemplate.registerListener(kaKaoClientListener);

		return retryTemplate;
	}
}
