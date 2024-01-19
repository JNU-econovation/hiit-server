package com.hiit.api.domain.client.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component("restTemplateLoggingInterceptor")
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(
			HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		String sessionNumber = makeSessionNumber();
		printRequest(sessionNumber, request, body);
		ClientHttpResponse response = execution.execute(request, body);
		printResponse(sessionNumber, response);
		return response;
	}

	private String makeSessionNumber() {
		return Integer.toString((int) (Math.random() * 1000000));
	}

	private void printRequest(final String sessionNumber, final HttpRequest req, final byte[] body) {
		log.info(
				"[{}] URI: {}, Method: {}, Headers:{}, Body:{} ",
				sessionNumber,
				req.getURI(),
				req.getMethod(),
				req.getHeaders(),
				new String(body, StandardCharsets.UTF_8));
	}

	private void printResponse(final String sessionNumber, final ClientHttpResponse res)
			throws IOException {
		String body =
				new BufferedReader(new InputStreamReader(res.getBody(), StandardCharsets.UTF_8))
						.lines()
						.collect(Collectors.joining("\n"));

		log.info(
				"[{}] Status: {}, Headers:{}, Body:{} ",
				sessionNumber,
				res.getStatusCode(),
				res.getHeaders(),
				body);
	}
}
