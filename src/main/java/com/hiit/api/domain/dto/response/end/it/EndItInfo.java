package com.hiit.api.domain.dto.response.end.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import java.time.LocalTime;
import java.util.Date;
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
public class EndItInfo implements ServiceResponse {

	private Long id;
	private String title;
	private String topic;
	private LocalTime startTime;
	private LocalTime endTime;
	private Date startDate;
	private Date endDate;
	private Long withCount;
}
