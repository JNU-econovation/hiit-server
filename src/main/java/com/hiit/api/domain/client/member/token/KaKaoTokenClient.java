package com.hiit.api.domain.client.member.token;

import com.hiit.api.domain.client.config.KaKaoApiProperties;
import com.hiit.api.domain.client.exception.SocialClientException;
import com.hiit.api.domain.client.response.token.KaKaoIdToken;
import com.hiit.api.domain.client.response.token.Token;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KaKaoTokenClient implements SocialTokenClient {

	private final RestTemplate restTemplate;
	private final KaKaoApiProperties properties;

	@Override
	public Token execute(String code) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", properties.getClientId());
		params.add("redirect_uri", properties.getRedirectUri());
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
		ResponseEntity<KaKaoIdToken> response = null;
		try {
			response =
					restTemplate.exchange(
							properties.getUriToken(), HttpMethod.POST, httpEntity, KaKaoIdToken.class);
		} catch (RestClientException e) {
			throw new SocialClientException();
		}

		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.is4xxClientError()) {
			throw new SocialClientException();
		}

		return Objects.requireNonNull(response).getBody();
	}
}
