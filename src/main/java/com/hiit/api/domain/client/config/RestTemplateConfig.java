package com.hiit.api.domain.client.config;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

	private final ClientHttpRequestInterceptor restTemplateClientHttpRequestInterceptor;
	private final ClientHttpRequestInterceptor restTemplateLoggingInterceptor;

	@Bean
	public RestTemplate restTemplate(
			@Value("${client.timeout.connect}") Integer connectTimeout,
			@Value("${client.timeout.read}") Integer readTimeout,
			@Value("${client.pool.max-connect}") Integer maxConnectPool,
			@Value("${client.pool.max-connect-per-route}") Integer maxPerRouteConnectPool) {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(readTimeout);
		factory.setConnectTimeout(connectTimeout);
		HttpClient httpClient =
				HttpClientBuilder.create()
						.setMaxConnTotal(maxConnectPool)
						.setMaxConnPerRoute(maxPerRouteConnectPool)
						.build();
		factory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (interceptors.isEmpty()) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(restTemplateClientHttpRequestInterceptor);
		interceptors.add(restTemplateLoggingInterceptor);

		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}
}
