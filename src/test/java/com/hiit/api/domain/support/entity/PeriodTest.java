package com.hiit.api.domain.support.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PeriodTest {

	@Test
	@DisplayName("주어진 시간이 기간 내에 있는지 확인한다.")
	void isValid() {
		// given
		LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(2020, 1, 1, 23, 59, 59);
		Period period = Period.builder().start(start).end(end).build();

		// when
		LocalDateTime time = LocalDateTime.of(2020, 1, 1, 12, 0, 0);

		// then
		assertTrue(period.isValid(time));
	}

	@Test
	@DisplayName("주어진 시간이 기간 내에 없는지 확인한다.")
	void isNotValid() {
		// given
		LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(2020, 1, 1, 23, 59, 59);
		Period period = Period.builder().start(start).end(end).build();

		// when
		LocalDateTime time = LocalDateTime.of(2020, 1, 2, 12, 0, 0);

		// then
		assertFalse(period.isValid(time));
	}
}
