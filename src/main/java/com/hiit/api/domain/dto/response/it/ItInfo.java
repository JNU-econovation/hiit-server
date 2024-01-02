package com.hiit.api.domain.dto.response.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
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
public class ItInfo implements AbstractResponse {

	private Long id;
	private String topic;
	private LocalTime startTime;
	private LocalTime endTime;
	private Long inMemberCount;
	private Boolean memberIn;
}
