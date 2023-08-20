package com.hiit.api.repository.entity.business.supporter;

import com.hiit.api.repository.entity.business.FooEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FooEntitySupporter {

	public FooEntity getIdEntity(Long id) {
		return FooEntity.builder().id(id).build();
	}

	public FooEntity touch(FooEntity source) {
		Long count = source.getCount();
		if (count.compareTo(0L) <= 0) {
			throw new RuntimeException("count is under zero");
		}
		return source.toBuilder().count(count - 1).build();
	}
}
