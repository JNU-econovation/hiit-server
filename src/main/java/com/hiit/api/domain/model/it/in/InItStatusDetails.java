package com.hiit.api.domain.model.it.in;

import lombok.Getter;

/** IT 상태 정보 */
@Getter
public enum InItStatusDetails {
	ACTIVE("active"),
	END("end");

	private String value;

	InItStatusDetails(String value) {
		this.value = value;
	}
}
