package com.hiit.api.domain.exception;

/** 생성 횟수 정책 위반 예외 */
public class CreateCountPolicyException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "생성 횟수 정책 위반 예외 발생하였습니다.";

	public CreateCountPolicyException() {
		super(DEFAULT_MESSAGE);
	}

	public CreateCountPolicyException(int count) {
		super(DEFAULT_MESSAGE + " count : " + count);
	}
}
