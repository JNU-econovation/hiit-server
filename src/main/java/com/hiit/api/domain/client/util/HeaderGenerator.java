package com.hiit.api.domain.client.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

@UtilityClass
public class HeaderGenerator {

	public static HttpHeaders generateBearerHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		return headers;
	}
}
