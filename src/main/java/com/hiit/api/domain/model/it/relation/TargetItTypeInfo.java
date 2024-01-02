package com.hiit.api.domain.model.it.relation;

import lombok.Getter;

/** IT 유형 정보 */
@Getter
public enum TargetItTypeInfo {
	REGISTERED_IT("registered", true),
	FOR_TEST("test", false),
	;

	private String type;
	private Boolean root;

	TargetItTypeInfo(String type, Boolean root) {
		this.type = type;
		this.root = root;
	}

	public static TargetItTypeInfo of(String type) {
		for (TargetItTypeInfo value : TargetItTypeInfo.values()) {
			if (value.getType().equals(type)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid type. type: " + type);
	}
}
