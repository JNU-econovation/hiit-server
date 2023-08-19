package com.hiit.api.domain.usecase.foo;

import com.hiit.api.repository.dao.bussiness.FooRepository;
import com.hiit.api.repository.entity.business.FooEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountFooService {

	private final FooRepository fooRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long execute(Long id) {
		FooEntity fooEntity =
				fooRepository.findById(id).orElseThrow(() -> new RuntimeException("Foo not found"));
		fooEntity.touch();
		return fooEntity.getCount();
	}
}
