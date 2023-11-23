package com.hiit.api.common.exception;

public enum ExceptionSpec {
	FAIL("정의되지 않은 오류인 경우", 400, "fail"),
	FAIL_AUTHENTICATION("인증되지 않은 경우", 401, "fail.authentication"),
	FAIL_NOT_FOUND("서버 내부에서 처리할 코드가 없는 경우", 404, "fail.notFound"),
	REQUEST_INVALID("유효하지 않은 요청인 경우", 400, "request.invalid"),
	REQUEST_INVALID_PARAMETER("유효하지 않은 정보를 포함한 요청인 경우", 400, "request.$s.parameter"),
	ACCESS_DENIED("접근이 제한된 경우", 403, "access.denied"),
	RESOURCE_NOT_FOUND("요청에 대한 자원이 없는 경우", 404, "resource.notFound"),
	RESOURCE_DELETED("요청에 대한 자원이 삭제된 경우", 404, "resource.deleted"),
	FAIL_INTERNAL("서버 내부 오류인 경우", 500, "fail"),
	;
	private final String situation;
	private final Integer httpCode;
	private final String code;

	ExceptionSpec(String situation, Integer httpCode, String code) {
		this.situation = situation;
		this.httpCode = httpCode;
		this.code = code;
	}

	public String getSituation() {
		return situation;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	public String getCode() {
		return code;
	}
}
