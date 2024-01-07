package com.hiit.api.domain.exception;

/** 잘못된 요청이 들어왔을 때 발생하는 예외 */
public class BadRequestException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "잘못된 요청입니다.";

	public BadRequestException() {
		super(DEFAULT_MESSAGE);
	}
}
