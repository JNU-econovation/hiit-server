package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.client.member.token.RetryAbleKaKaoTokenClient;
import com.hiit.api.domain.client.response.token.KaKaoIdTokenProperties;
import com.hiit.api.domain.client.response.token.Token;
import com.hiit.api.domain.exception.SocialIntegrationException;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.token.KaKaoIdTokenParser;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateKaKaoMemberUseCase {

	private final RetryAbleKaKaoTokenClient kaKaoTokenClient;
	private final KaKaoIdTokenParser kaKaoIdTokenParser;

	public Member execute(final String code) {
		Token token = kaKaoTokenClient.execute(code);
		if (Objects.isNull(token.getToken())) {
			log.error("token is null");
			throw new SocialIntegrationException();
		}

		String idToken = token.getToken();
		KaKaoIdTokenProperties idTokenProperties = kaKaoIdTokenParser.parse(idToken);
		String nickname = idTokenProperties.getNickname();
		String certificationId = idTokenProperties.getSub();

		return Member.builder().nickName(nickname).certificationId(certificationId).build();
	}
}
