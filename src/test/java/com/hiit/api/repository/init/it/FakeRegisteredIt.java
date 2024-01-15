package com.hiit.api.repository.init.it;

import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import java.time.LocalDateTime;

public class FakeRegisteredIt {

	public FakeRegisteredIt() {}

	public static RegisteredItEntity create() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start =
				LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end =
				LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
		return RegisteredItEntity.builder()
				.topic("tTopic1")
				.startTime(start.toLocalTime())
				.endTime(end.toLocalTime())
				.build();
	}
}
