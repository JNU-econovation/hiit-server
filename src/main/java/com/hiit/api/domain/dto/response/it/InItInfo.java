package com.hiit.api.domain.dto.response.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import java.time.LocalTime;
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
public class InItInfo implements ServiceResponse {

	private Long id;
	private String title;
	private String topic;
	private LocalTime startTime;
	private LocalTime endTime;
	private String days; // todo fix to dayCode
	private Long inMemberCount;
}
