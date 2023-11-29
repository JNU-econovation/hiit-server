package com.hiit.api.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hiit.api.web.dto.validator.TimeDividedBy30Minutes;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Builder
public class SuggestItRequest {

	@NotNull
	@Size(min = 1, max = 50)
	String title;

	@JsonFormat(pattern = "HH:mm")
	@TimeDividedBy30Minutes
	LocalTime start;

	@JsonFormat(pattern = "HH:mm")
	@TimeDividedBy30Minutes
	LocalTime end;

	@NotNull
	@Size(min = 1, max = 200)
	String reason;
}
