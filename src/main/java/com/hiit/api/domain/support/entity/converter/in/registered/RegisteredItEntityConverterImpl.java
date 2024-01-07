package com.hiit.api.domain.support.entity.converter.in.registered;

import com.hiit.api.domain.model.it.registered.RegisteredIt;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.usecase.it.RegisteredItEntityConverter;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.stereotype.Component;

@Component
public class RegisteredItEntityConverterImpl implements RegisteredItEntityConverter {

	@Override
	public RegisteredIt from(RegisteredItEntity entity) {
		return RegisteredIt.builder()
				.id(entity.getId())
				.topic(entity.getTopic())
				.startTime(entity.getStartTime())
				.endTime(entity.getEndTime())
				.type(TargetItTypeInfo.REGISTERED_IT)
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	@Override
	public RegisteredItEntity to(RegisteredIt data) {
		return RegisteredItEntity.builder()
				.topic(data.getTopic())
				.startTime(data.getStartTime())
				.endTime(data.getEndTime())
				.build();
	}

	@Override
	public RegisteredItEntity to(Long id, RegisteredIt data) {
		return to(data).toBuilder().id(id).build();
	}
}
