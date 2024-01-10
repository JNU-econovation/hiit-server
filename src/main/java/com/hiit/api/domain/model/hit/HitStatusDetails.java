package com.hiit.api.domain.model.hit;

import lombok.Getter;

/** 히트 상태 정보 */
@Getter
public enum HitStatusDetails {
	HIT("hit"),
	MISS("miss");

	private String value;

	HitStatusDetails(String value) {
		this.value = value;
	}
}
