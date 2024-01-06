package com.hiit.api.domain.dto.response.member;

import com.hiit.api.common.marker.dto.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserAuthToken implements AbstractResponse {

	private String accessToken;
	private String refreshToken;
}
