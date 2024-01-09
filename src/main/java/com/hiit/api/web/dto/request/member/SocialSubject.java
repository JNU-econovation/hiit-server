package com.hiit.api.web.dto.request.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;
import lombok.Getter;

@Getter
public enum SocialSubject implements Serializable {
	KAKAO("kakao"),
	;

	private String value;

	SocialSubject(String value) {
		this.value = value;
	}

	// todo: JsonCreator 안쓰고 리펙토링 생각
	@JsonCreator
	public static SocialSubject of(String source) {
		if (source.equals("kakao")) {
			return SocialSubject.KAKAO;
		}
		return null;
	}
}
