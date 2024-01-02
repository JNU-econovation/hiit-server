package com.hiit.api.support;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

/** API 응답 객체 생성기 */
@UtilityClass
public class ApiResponseGenerator {

	/**
	 * Http 상태 코드만 포함하는 API 응답 성공 객체 생성한다.
	 *
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 */
	public static ApiResponse<ApiResponse.Success> success(final HttpStatus status) {
		return new ApiResponse<>(
				new ApiResponse.Success(MessageCode.SUCCESS.getValue(), MessageCode.SUCCESS.getCode()),
				status);
	}

	/**
	 * Http 상태 코드와 메시지 코드를 포함하는 API 응답 성공 객체 생성한다.
	 *
	 * @param status Http 상태 코드
	 * @param code 메시지 코드
	 * @return API 응답 객체
	 */
	public static ApiResponse<ApiResponse.Success> success(
			final HttpStatus status, MessageCode code) {
		return new ApiResponse<>(new ApiResponse.Success(code.getValue(), code.getCode()), status);
	}

	/**
	 * Http 상태 코드와 데이터를 포함하는 API 응답 성공 객체 생성한다.
	 *
	 * @param data 데이터
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 * @param <D> 데이터 타입
	 */
	public static <D> ApiResponse<ApiResponse.SuccessBody<D>> success(
			final D data, final HttpStatus status) {
		return new ApiResponse<>(
				new ApiResponse.SuccessBody<>(
						data, MessageCode.SUCCESS.getValue(), MessageCode.SUCCESS.getCode()),
				status);
	}

	/**
	 * Http 상태 코드, 데이터, 메시지 코드를 포함하는 API 응답 성공 객체 생성한다.
	 *
	 * @param data 데이터
	 * @param status Http 상태 코드
	 * @param code 메시지 코드
	 * @return API 응답 객체
	 * @param <D> 데이터 타입
	 */
	public static <D> ApiResponse<ApiResponse.SuccessBody<D>> success(
			final D data, final HttpStatus status, MessageCode code) {
		return new ApiResponse<>(
				new ApiResponse.SuccessBody<>(data, code.getValue(), code.getCode()), status);
	}

	/**
	 * Http 상태 코드와 데이터(페이지)를 포함하는 API 응답 성공 객체 생성한다.
	 *
	 * @param data 데이터(페이지)
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 * @param <D> 데이터 타입
	 */
	public static <D> ApiResponse<Page<D>> success(
			final org.springframework.data.domain.Page<D> data, final HttpStatus status) {
		return new ApiResponse<>(new Page<>(data), status);
	}

	/**
	 * Http 상태 코드만 포함하는 API 응답 실패 객체 생성한다.
	 *
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 */
	public static ApiResponse<Void> fail(final HttpStatus status) {
		return new ApiResponse<>(status);
	}

	/**
	 * Http 상태 코드와 응답 실패 바디를 포함하는 API 응답 실패 객체 생성한다.
	 *
	 * @param body 응답 실패 바디
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 */
	public static ApiResponse<ApiResponse.FailureBody> fail(
			final ApiResponse.FailureBody body, final HttpStatus status) {
		return new ApiResponse<>(body, status);
	}

	/**
	 * Http 상태 코드와 코드 그리고 응답 실패 바디를 포함하는 API 응답 실패 객체 생성한다.
	 *
	 * @param code 코드
	 * @param message 메시지
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 */
	public static ApiResponse<ApiResponse.FailureBody> fail(
			final String code, final String message, final HttpStatus status) {
		return new ApiResponse<>(new ApiResponse.FailureBody(code, message), status);
	}

	/**
	 * Http 상태 코드와 메시지를 포함하는 API 응답 실패 객체 생성한다.
	 *
	 * @param message 메시지
	 * @param status Http 상태 코드
	 * @return API 응답 객체
	 */
	public static ApiResponse<ApiResponse.FailureBody> fail(
			final String message, final HttpStatus status) {
		return new ApiResponse<>(new ApiResponse.FailureBody(message), status);
	}
}
