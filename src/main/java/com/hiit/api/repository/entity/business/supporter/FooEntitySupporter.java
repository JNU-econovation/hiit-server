package com.hiit.api.repository.entity.business.supporter;

import com.hiit.api.repository.entity.business.FooEntity;
import org.springframework.stereotype.Component;

@Component
public class FooEntitySupporter {

	public FooEntity getIdEntity(Long id) {
		return FooEntity.builder().id(id).build();
	}
}
