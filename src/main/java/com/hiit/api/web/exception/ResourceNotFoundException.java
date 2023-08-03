package com.hiit.api.web.exception;

import javax.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

/** 리소스 조회 실패시 발생하는 예외 */
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(@NotEmpty final String message) {
		super(message);
	}

	public ResourceNotFoundException(@NotEmpty final String message, final Throwable cause) {
		super(message, cause);
	}
}
