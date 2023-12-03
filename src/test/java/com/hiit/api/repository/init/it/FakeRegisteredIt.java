package com.hiit.api.repository.init.it;

import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import java.time.LocalTime;

public class FakeRegisteredIt {

	public FakeRegisteredIt() {}

	public static RegisteredItEntity create() {
		return RegisteredItEntity.builder()
				.topic("tTopic1")
				.startTime(LocalTime.of(7, 0))
				.endTime(LocalTime.of(9, 0))
				.build();
	}
}
