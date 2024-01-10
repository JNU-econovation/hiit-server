package com.hiit.api.common.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** 인증 토큰 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WebAuthToken implements AuthToken {

	private String accessToken;
	private String refreshToken;
}
