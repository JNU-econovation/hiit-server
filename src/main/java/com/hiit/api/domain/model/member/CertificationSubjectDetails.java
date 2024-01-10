package com.hiit.api.domain.model.member;

import lombok.Getter;

/** 인증 주체 정보 */
@Getter
public enum CertificationSubjectDetails {
	KAKAO("kakao");

	private String value;

	CertificationSubjectDetails(String value) {
		this.value = value;
	}
}
