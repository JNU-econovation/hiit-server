package com.hiit.api.domain.support.entity;

import com.hiit.api.domain.model.ItTimeInfo;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PeriodUtils {

	public static Period make(ItTimeInfo timeSource, LocalDateTime date) {
		return PeriodUtils.make(timeSource, date, date);
	}

	public static Period make(ItTimeInfo timeSource, LocalDateTime startDate, LocalDateTime endDate) {
		LocalTime startTime = timeSource.getStartTime();
		LocalTime endTime = timeSource.getEndTime();
		LocalDateTime startDateTime = LocalDateTime.of(startDate.toLocalDate(), startTime);
		LocalDateTime endDateTime = LocalDateTime.of(endDate.toLocalDate(), endTime);
		return Period.builder().start(startDateTime).end(endDateTime).build();
	}
}
