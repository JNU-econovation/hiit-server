package com.hiit.api.domain.exception;

/** 데이터 조회 오류 예외 */
public class DataNotFoundException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "Data not found";

	public DataNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public DataNotFoundException(String data) {
		super(data + " " + DEFAULT_MESSAGE);
	}
}
