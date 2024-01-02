package com.hiit.api.domain.model.member;

import lombok.Getter;
import lombok.ToString;

/** 회원 상태 정보 */
@ToString
@Getter
public enum MemberStatusInfo {
	REGULAR("정회원"),
	ASSOCIATE("준회원"),
	SEPARATE("장기미이용 회원"),
	WITHDRAWN("탈퇴회원"),
	;

	private final String description;

	MemberStatusInfo(String description) {
		this.description = description;
	}
}
