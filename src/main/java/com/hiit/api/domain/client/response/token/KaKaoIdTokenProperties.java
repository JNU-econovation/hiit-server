package com.hiit.api.domain.client.response.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KaKaoIdTokenProperties {

	//	iss: ID 토큰을 발급한 인증 기관 정보, https://kauth.kakao.com로 고정
	private String iss;
	//	aud: ID 토큰이 발급된 앱의 앱 키
	private String aud;
	//	sub: ID 토큰에 해당하는 사용자의 회원번호
	private String sub;
	//	nickname: 닉네임
	private String nickname;
}
