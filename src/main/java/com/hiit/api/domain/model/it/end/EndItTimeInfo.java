package com.hiit.api.domain.model.it.end;

import com.hiit.api.domain.model.ItTimeInfo;
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
public class EndItTimeInfo implements ItTimeInfo {

	LocalTime startTime;
	LocalTime endTime;
}
