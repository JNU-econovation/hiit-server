package com.hiit.api.client.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = FeignConfig.BASE_PACKAGE)
@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpClientConfiguration.class})
public class FeignConfig {

	public static final String BASE_PACKAGE = "com.hiit.api.client";

	@Bean
	HttpMessageConverters httpMessageConverters() {
		return new HttpMessageConverters();
	}
}
