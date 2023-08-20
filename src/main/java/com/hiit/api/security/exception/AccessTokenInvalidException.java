package com.hiit.api.security.exception;

import org.springframework.security.core.AuthenticationException;

/** 토큰이 유효하지 않을 때 발생하는 예외 */
public class AccessTokenInvalidException extends AuthenticationException {
	public AccessTokenInvalidException(String msg) {
		super(msg);
	}
}
