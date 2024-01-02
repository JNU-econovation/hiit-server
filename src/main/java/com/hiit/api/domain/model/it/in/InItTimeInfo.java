package com.hiit.api.domain.model.it.in;

import com.hiit.api.domain.model.ItTimeInfo;
import java.time.LocalTime;
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
public class InItTimeInfo implements ItTimeInfo {

	private LocalTime startTime;
	private LocalTime endTime;
}
