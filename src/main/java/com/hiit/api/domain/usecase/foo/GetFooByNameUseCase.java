package com.hiit.api.domain.usecase.foo;

import com.hiit.api.repository.dao.bussiness.FooRepository;
import com.hiit.api.repository.entity.business.FooEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetFooByNameUseCase {

	private final FooRepository fooRepository;

	@Transactional
	public FooEntity execute(String name) {
		return fooRepository
				.findTopByName(name)
				.orElseThrow(() -> new RuntimeException("Foo not found"));
	}
}
