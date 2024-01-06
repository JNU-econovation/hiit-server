package com.hiit.api.domain.client.exception;

/** 클라이언트 4xx 에러 예외 */
public class SocialClientException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "클라이언트 에러가 발생하였습니다.";

	public SocialClientException() {
		super(DEFAULT_MESSAGE);
	}
}
