package com.hiit.api.domain.exception;

import com.hiit.api.domain.dao.support.Period;
import java.time.LocalDateTime;

/** 시간 정책 위반 예외 */
public class TimePolicyException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "Time policy violation";

	public TimePolicyException() {
		super(DEFAULT_MESSAGE);
	}

	public TimePolicyException(Period period, LocalDateTime now) {
		super(
				DEFAULT_MESSAGE
						+ " period "
						+ period.getStart().toString()
						+ " ~ "
						+ period.getEnd().toString()
						+ " now : "
						+ now);
	}
}
