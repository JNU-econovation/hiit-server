package com.hiit.api.domain.support.entity.converter.in.registered;

import com.hiit.api.domain.model.it.registered.It_Registered;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.usecase.it.RegisteredItEntityConverter;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.stereotype.Component;

@Component
public class RegisteredItEntityConverterImpl implements RegisteredItEntityConverter {

	@Override
	public It_Registered from(RegisteredItEntity entity) {
		return It_Registered.builder()
				.id(entity.getId())
				.topic(entity.getTopic())
				.startTime(entity.getStartTime())
				.endTime(entity.getEndTime())
				.type(ItTypeDetails.IT_REGISTERED)
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	@Override
	public RegisteredItEntity to(It_Registered data) {
		return RegisteredItEntity.builder()
				.topic(data.getTopic())
				.startTime(data.getStartTime())
				.endTime(data.getEndTime())
				.build();
	}

	@Override
	public RegisteredItEntity to(Long id, It_Registered data) {
		return to(data).toBuilder().id(id).build();
	}
}
