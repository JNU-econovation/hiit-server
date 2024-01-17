package com.hiit.api.repository.entity.business.it;

import lombok.Getter;

@Getter
public enum ItType {
	REGISTERED_IT("registered", true),
	FOR_TEST("test", false),
	;

	private String type;
	private Boolean root;

	ItType(String type, Boolean root) {
		this.type = type;
		this.root = root;
	}

	public static ItType of(String type) {
		for (ItType value : ItType.values()) {
			if (value.getType().equals(type)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid type. type: " + type);
	}
}
