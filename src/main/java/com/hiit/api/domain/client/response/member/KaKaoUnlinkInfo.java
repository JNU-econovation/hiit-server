package com.hiit.api.domain.client.response.member;

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
public class KaKaoUnlinkInfo implements UnlinkInfo {
	private String id;

	@Override
	public String getUnlinkInfo() {
		return this.id;
	}
}
