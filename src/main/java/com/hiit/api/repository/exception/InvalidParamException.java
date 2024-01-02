package com.hiit.api.repository.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;

public class InvalidParamException extends InvalidDataAccessApiUsageException {

	private static final String MESSAGE = "잘못된 파라미터입니다.";

	public InvalidParamException() {
		super(MESSAGE);
	}

	public InvalidParamException(String reason) {
		super(MESSAGE + " : " + reason);
	}
}
