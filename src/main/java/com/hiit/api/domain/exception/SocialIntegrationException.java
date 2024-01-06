package com.hiit.api.domain.exception;

// todo: exception handler에 등록
/** 소셜 연동 중 오류가 발생했을 때 발생하는 예외 */
public class SocialIntegrationException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "소셜 연동 중 오류가 발생했습니다.";

	public SocialIntegrationException() {
		super(DEFAULT_MESSAGE);
	}
}
