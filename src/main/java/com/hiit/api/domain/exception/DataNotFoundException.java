package com.hiit.api.domain.exception;

/** 데이터 조회 오류 예외 */
public class DataNotFoundException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "데이터를 조회할 수 없습니다.";

	public DataNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public DataNotFoundException(String data) {
		super(DEFAULT_MESSAGE + " data : " + data);
	}
}
