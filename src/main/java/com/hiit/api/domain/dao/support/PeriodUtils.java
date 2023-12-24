package com.hiit.api.domain.dao.support;

import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.service.hit.HitItTimeInfo;
import com.hiit.api.domain.service.with.WithItTimeInfo;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PeriodUtils {

	public static Period make(WithItTimeInfo timeSource, LocalDateTime now) {
		LocalTime startTime = timeSource.getStartTime();
		LocalTime endTime = timeSource.getEndTime();
		LocalDateTime startDateTime = LocalDateTime.of(now.toLocalDate(), startTime);
		LocalDateTime endDateTime = LocalDateTime.of(now.toLocalDate(), endTime);
		return Period.builder().start(startDateTime).end(endDateTime).build();
	}

	public static Period make(HitItTimeInfo timeSource) {
		LocalTime startTime = timeSource.getStartTime();
		LocalTime endTime = timeSource.getEndTime();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startDateTime = LocalDateTime.of(now.toLocalDate(), startTime);
		LocalDateTime endDateTime = LocalDateTime.of(now.toLocalDate(), endTime);
		return Period.builder().start(startDateTime).end(endDateTime).build();
	}

	public static Period make(WithData with) {
		LocalDateTime startTime = with.getCreateAt();
		LocalDateTime endTime = with.getUpdateAt();
		return Period.builder().start(startTime).end(endTime).build();
	}
}
