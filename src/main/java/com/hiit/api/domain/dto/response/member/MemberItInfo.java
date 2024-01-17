package com.hiit.api.domain.dto.response.member;

import com.hiit.api.common.marker.dto.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberItInfo implements AbstractResponse {

	private Long id;
	private String name;
	private String profile;
	private String itInfo;
}
