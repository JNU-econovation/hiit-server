package com.hiit.api.repository.entity.business.supporter;

import com.hiit.api.repository.entity.business.BarEntity;
import com.hiit.api.repository.entity.business.FooEntity;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class BarEntitySupporter {

	private static final Long DEFAULT_VERSION = 0L;

	public static BarEntity getIdEntity(Long id) {
		return BarEntity.builder().id(id).version(DEFAULT_VERSION).build();
	}

	public BarEntity registerFoo(BarEntity source, FooEntity foo) {
		if (Objects.nonNull(source.getFoo())) {
			source.getFoo().getBars().remove(source);
		}
		BarEntity updatedSource = source.toBuilder().foo(foo).build();
		foo.getBars().add(updatedSource);
		return updatedSource;
	}
}
