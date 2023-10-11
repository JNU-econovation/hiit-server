package com.hiit.api.domain.dto.response.member;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
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
public class ProfileResponse implements ServiceResponse {

	private Long id;
	private String name;
	private String comment;
	private String picture;
	private Long friendCount;
	private Long itInCount;
}
