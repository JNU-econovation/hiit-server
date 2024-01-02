package com.hiit.api.domain.dto.response.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class WithInfo implements AbstractResponse {

	private Long id;
	private String content;
	private Long hit;
	private WithMemberInfo withMemberInfo;
}
