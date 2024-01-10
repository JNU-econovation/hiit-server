package com.hiit.api.domain.model;

import java.time.LocalTime;

/** IT의 시간 정보 */
public interface ItTimeDetails {

	LocalTime getStartTime();

	LocalTime getEndTime();
}