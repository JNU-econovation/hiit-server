package com.hiit.api.domain.service.token;

import com.hiit.api.domain.client.config.KaKaoProperties;
import com.hiit.api.domain.client.response.token.KaKaoIdTokenProperties;
import com.hiit.api.domain.client.util.TokenPropertiesMapper;
import com.hiit.api.domain.exception.SocialIntegrationException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KaKaoIdTokenParser {

	public static final int payloadIndex = 1;

	private final KaKaoProperties kaKaoProperties;
	private final TokenPropertiesMapper propertiesMapper;

	public KaKaoIdTokenProperties parse(String idToken) {
		String payload = idToken.split("\\.")[payloadIndex];
		String source = new String(Base64.getDecoder().decode(payload));
		KaKaoIdTokenProperties idProperties =
				propertiesMapper.read(source, KaKaoIdTokenProperties.class);
		if (!idProperties.getIss().equals(kaKaoProperties.getHost())) {
			log.error("Invalid host(iss). host: {}", idProperties.getIss());
			throw new SocialIntegrationException();
		}
		if (!idProperties.getAud().equals(kaKaoProperties.getClientId())) {
			log.error("Invalid client id(aud). client id: {}", idProperties.getAud());
			throw new SocialIntegrationException();
		}
		return idProperties;
	}
}
