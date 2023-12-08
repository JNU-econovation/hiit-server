package com.hiit.api.domain.dao.member;

import lombok.Getter;
import lombok.ToString;

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
