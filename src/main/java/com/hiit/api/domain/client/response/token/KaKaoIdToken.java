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
public class KaKaoIdToken implements Token {

	// todo fix this to idToken
	private String id_token;

	@Override
	public String getToken() {
		return this.id_token;
	}
}
