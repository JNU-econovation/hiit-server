package com.hiit.api.domain.support.entity;

import java.time.LocalDateTime;
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
public class Period {

	private LocalDateTime start;
	private LocalDateTime end;

	public static Period today() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start =
				LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end =
				LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
		return Period.builder().start(start).end(end).build();
	}

	public boolean isValid(LocalDateTime time) {
		if (!start.isBefore(time)) {
			return false;
		}
		if (!end.isAfter(time)) {
			return false;
		}
		return true;
	}
}
