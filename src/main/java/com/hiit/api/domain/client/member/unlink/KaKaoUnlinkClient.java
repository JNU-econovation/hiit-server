package com.hiit.api.domain.client.member.unlink;

import com.hiit.api.domain.client.config.KaKaoApiProperties;
import com.hiit.api.domain.client.exception.SocialClientException;
import com.hiit.api.domain.client.response.member.KaKaoUnlinkInfo;
import com.hiit.api.domain.client.response.member.UnlinkInfo;
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
public class KaKaoUnlinkClient implements SocialUnlinkClient {

	private final RestTemplate restTemplate;
	private final KaKaoApiProperties properties;

	@Override
	public UnlinkInfo execute(String targetId) {
		String adminKey = properties.getAdminKey();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		headers.add(HttpHeaders.AUTHORIZATION, "KakaoAK " + adminKey);

		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
		params.add("target_id", Long.parseLong(targetId));
		ResponseEntity<KaKaoUnlinkInfo> response = null;
		try {
			response =
					restTemplate.exchange(
							properties.getUnlink(),
							HttpMethod.POST,
							new HttpEntity<>(params, headers),
							KaKaoUnlinkInfo.class);
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
