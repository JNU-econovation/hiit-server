package com.hiit.api.domain.dto.response.member;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
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
public class MemberInfo implements ServiceResponse {

	private Long id;
	private String name;
	private String profile;
	private Long inItCount;
}
