package com.hiit.api.domain.dao.bar;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.BarEntity;
import org.springframework.stereotype.Component;

@Component
public class BarDataConverter implements AbstractDataConverter<BarEntity, BarData> {

	@Override
	public BarData from(BarEntity entity) {
		return BarData.builder()
				.id(entity.getId())
				.name(entity.getName())
				.foo(entity.getFoo().getId())
				.build();
	}

	public BarEntity to(BarData data) {
		return BarEntity.builder().id(data.getId()).name(data.getName()).build();
	}
}
