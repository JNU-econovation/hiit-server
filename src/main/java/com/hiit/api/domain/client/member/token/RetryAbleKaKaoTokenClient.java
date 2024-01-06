package com.hiit.api.domain.client.member.token;

import com.hiit.api.domain.client.exception.SocialClientException;
import com.hiit.api.domain.client.response.token.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryAbleKaKaoTokenClient implements SocialTokenClient {

	private final RetryTemplate kakaoRetryTemplate;
	private final KaKaoTokenClient kaKaoTokenClient;

	@Override
	public Token execute(String code) {
		return kakaoRetryTemplate.execute(
				new RetryCallback<Token, SocialClientException>() {
					@Override
					public Token doWithRetry(RetryContext retryContext) throws SocialClientException {
						log.error("something wrong at kakao api");
						log.error("kakao get token retry count: {}", retryContext.getRetryCount());
						return kaKaoTokenClient.execute(code);
					}
				},
				new RecoveryCallback<Token>() {
					@Override
					public Token recover(RetryContext retryContext) throws Exception {
						// todo 실패 기록
						return null;
					}
				});
	}
}
