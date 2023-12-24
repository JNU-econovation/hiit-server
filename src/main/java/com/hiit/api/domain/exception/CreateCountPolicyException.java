package com.hiit.api.domain.exception;

/** 생성 횟수 정책 위반 예외 */
public class CreateCountPolicyException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "Create count policy violation";

	public CreateCountPolicyException() {
		super(DEFAULT_MESSAGE);
	}

	public CreateCountPolicyException(int count) {
		super(DEFAULT_MESSAGE + " current created count : " + count);
	}
}
