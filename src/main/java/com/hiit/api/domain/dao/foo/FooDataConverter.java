package com.hiit.api.domain.dao.foo;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.BaseEntity;
import com.hiit.api.repository.entity.business.FooEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FooDataConverter implements AbstractDataConverter<FooEntity, FooData> {

	@Override
	public FooData from(FooEntity entity) {
		List<Long> bars = new ArrayList<>();
		for (BaseEntity bar : entity.getBars()) {
			bars.add(bar.getId());
		}
		return FooData.builder()
				.id(entity.getId())
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.deleted(entity.getDeleted())
				.name(entity.getName())
				.bars(bars)
				.build();
	}

	public FooEntity to(FooData data) {
		return FooEntity.builder().id(data.getId()).name(data.getName()).build();
	}
}
