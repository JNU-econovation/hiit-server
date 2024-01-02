package com.hiit.api.domain.exception;

import java.time.LocalDateTime;

/** 시간 정책 위반 예외 */
public class TimePolicyException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "시간 정책 위반 예외 발생하였습니다.";

	public TimePolicyException() {
		super(DEFAULT_MESSAGE + " 현재 시간 : " + LocalDateTime.now());
	}
}
