package com.hiit.api.web.dto.request.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;

public enum SocialSubject implements Serializable {
	KAKAO;

	// todo: JsonCreator 안쓰고 리펙토링 생각
	@JsonCreator
	public static SocialSubject of(String source) {
		if (source.equals("kakao")) {
			return SocialSubject.KAKAO;
		}
		return null;
	}
}
