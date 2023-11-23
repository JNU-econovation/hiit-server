package com.hiit.api.web.support;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** API 응답 객체 */
@Getter
public class ApiResponse<B> extends ResponseEntity<B> {

	public ApiResponse(final HttpStatus status) {
		super(status);
	}

	public ApiResponse(final B body, final HttpStatus status) {
		super(body, status);
	}

	/** API 응답 실패 객체 */
	@Getter
	@AllArgsConstructor
	public static class FailureBody implements Serializable {

		private String code;
		private String message;

		public FailureBody(final String message) {
			this.message = message;
		}
	}

	/**
	 * API 응답 성공 객체
	 *
	 * @param <D> 데이터 타입
	 */
	@Getter
	@AllArgsConstructor
	public static class SuccessBody<D> implements Serializable {
		private D data;
		private String message;
		private String code;
	}

	/** API 응답 성공 객체 */
	@Getter
	@AllArgsConstructor
	public static class Success implements Serializable {
		private String message;
		private String code;
	}
}
