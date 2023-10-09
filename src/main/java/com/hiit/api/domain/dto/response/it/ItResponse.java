package com.hiit.api.domain.dto.response.it;

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
public class ItResponse implements ServiceResponse {

	private Long id;
	private String topic;
	private Long startTime;
	private Long endTime;
	private Long participatePerson;
}
