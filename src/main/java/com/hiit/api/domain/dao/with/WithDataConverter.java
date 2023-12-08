package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import org.springframework.stereotype.Component;

@Component
public class WithDataConverter implements AbstractDataConverter<WithEntity, WithData> {

	@Override
	public WithData from(WithEntity entity) {
		return WithData.builder().content(entity.getContent()).inItId(entity.getInIt().getId()).build();
	}

	public WithEntity to(WithData data) {
		return WithEntity.builder()
				.content(data.getContent())
				.inIt(InItEntity.builder().id(data.getInItId()).build())
				.build();
	}
}
