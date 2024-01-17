package com.hiit.api.domain.model.it.relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/** IT 유형 정보 */
@Getter
public enum ItTypeDetails {
	IT_REGISTERED("registered", true),
	FOR_TEST("test", false),
	;

	private String value;
	private Boolean root;

	ItTypeDetails(String value, Boolean root) {
		this.value = value;
		this.root = root;
	}

	@JsonCreator
	public static ItTypeDetails of(String type) {
		for (ItTypeDetails value : ItTypeDetails.values()) {
			if (value.getValue().equals(type)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid type. type: " + type);
	}
}
