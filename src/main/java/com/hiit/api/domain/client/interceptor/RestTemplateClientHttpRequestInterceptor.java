package com.hiit.api.domain.client.interceptor;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component("restTemplateClientHttpRequestInterceptor")
public class RestTemplateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	@Value("${client.max-attempts}")
	private int maxAttempts;

	@Override
	public ClientHttpResponse intercept(
			HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(new SimpleRetryPolicy(maxAttempts));
		try {
			return retryTemplate.execute(context -> execution.execute(request, body));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
