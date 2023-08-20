package com.hiit.api.domain.usecase.foo;

import com.hiit.api.repository.dao.bussiness.FooRepository;
import com.hiit.api.repository.entity.business.FooEntity;
import com.hiit.api.repository.entity.business.supporter.FooEntitySupporter;
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
	private final FooEntitySupporter fooEntitySupporter;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long execute(Long id) {
		FooEntity fooEntity =
				fooRepository.findById(id).orElseThrow(() -> new RuntimeException("Foo not found"));
		FooEntity updateFooEntity = fooEntitySupporter.touch(fooEntity);
		return fooRepository.save(updateFooEntity).getCount();
	}
}
