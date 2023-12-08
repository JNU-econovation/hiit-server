package com.hiit.api.domain.dao.it.relation;

import lombok.Getter;

@Getter
public enum TargetItTypeInfo {
	REGISTERED_IT("registered", true),
	;

	private String type;
	private Boolean root;

	TargetItTypeInfo(String type, Boolean root) {
		this.type = type;
		this.root = root;
	}
}
