package com.hiit.api.domain.model.member;

import lombok.Getter;
import lombok.ToString;

/** 회원 상태 정보 */
@ToString
@Getter
public enum MemberStatusDetails {
	REGULAR("regular", "정회원"),
	ASSOCIATE("associate", "준회원"),
	SEPARATE("separate", "장기미이용 회원"),
	WITHDRAWN("withdrawn", "탈퇴회원"),
	;

	private final String value;
	private final String description;

	MemberStatusDetails(String value, String description) {
		this.value = value;
		this.description = description;
	}
}
