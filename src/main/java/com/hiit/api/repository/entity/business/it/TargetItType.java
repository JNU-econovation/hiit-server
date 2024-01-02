package com.hiit.api.repository.entity.business.it;

import lombok.Getter;

@Getter
public enum TargetItType {
	REGISTERED_IT("registered", true),
	FOR_TEST("test", false),
	;

	private String type;
	private Boolean root;

	TargetItType(String type, Boolean root) {
		this.type = type;
		this.root = root;
	}

	public static TargetItType of(String type) {
		for (TargetItType value : TargetItType.values()) {
			if (value.getType().equals(type)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid type. type: " + type);
	}
}
