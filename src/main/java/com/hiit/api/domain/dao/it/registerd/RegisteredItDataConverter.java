package com.hiit.api.domain.dao.it.registerd;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.stereotype.Component;

@Component
public class RegisteredItDataConverter
		implements AbstractDataConverter<RegisteredItEntity, RegisteredItData> {

	@Override
	public RegisteredItData from(RegisteredItEntity entity) {
		return RegisteredItData.builder()
				.id(entity.getId())
				.topic(entity.getTopic())
				.startTime(entity.getStartTime())
				.endTime(entity.getEndTime())
				.build();
	}

	public RegisteredItEntity to(RegisteredItData data) {
		return RegisteredItEntity.builder()
				.topic(data.getTopic())
				.startTime(data.getStartTime())
				.endTime(data.getEndTime())
				.build();
	}
}
