package com.hiit.api.domain.exception;

/** 멤버가 접근할 수 없는 리소스에 접근했을 때 발생하는 예외 */
public class MemberAccessDeniedException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "Member access denied";

	public MemberAccessDeniedException() {
		super(DEFAULT_MESSAGE);
	}

	public MemberAccessDeniedException(String reason) {
		super(DEFAULT_MESSAGE + " " + reason);
	}
}
