package com.hiit.api.repository.entity.business.it;

import lombok.Getter;

@Getter
public enum TargetItType {
	REGISTERED_IT("registered", true),
	;

	private String type;
	private Boolean root;

	TargetItType(String type, Boolean root) {
		this.type = type;
		this.root = root;
	}
}
