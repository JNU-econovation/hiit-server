package com.hiit.api.web.dto.request.it;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;
import lombok.Getter;

@Getter
public enum RequestItType implements Serializable {
	REGISTERED_IT("registered"),
	;

	private String value;

	RequestItType(String value) {
		this.value = value;
	}

	// todo: JsonCreator 안쓰고 리펙토링 생각
	@JsonCreator
	public static RequestItType of(String source) {
		if (source.equals("registered")) {
			return RequestItType.REGISTERED_IT;
		}
		return null;
	}
}
