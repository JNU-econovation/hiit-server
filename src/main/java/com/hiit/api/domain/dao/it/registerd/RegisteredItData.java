package com.hiit.api.domain.dao.it.registerd;

import com.hiit.api.domain.dao.BaseData;
import java.time.LocalTime;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class RegisteredItData extends BaseData {

	private String topic;
	private LocalTime startTime;
	private LocalTime endTime;
}
