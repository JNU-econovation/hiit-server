package com.hiit.api.domain.dto.response.noti;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import java.time.LocalDate;
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
public class NotiResponse implements ServiceResponse {

	private Long id;
	private LocalDate date;
	// todo enum
	private String type;
	private String title;
	private String content;
}
