package com.hiit.api.domain.support.foo.converter;

import com.hiit.api.domain.dto.response.FooUseCaseResponse;
import com.hiit.api.domain.model.Foo;
import com.hiit.api.domain.support.AbstractDomainConverter;
import com.hiit.api.domain.usecase.foo.SaveFooUseCase.SampleEntity;
import org.springframework.stereotype.Component;

/** 유즈케이스 계층에서 사용하는 도메인 객체 관련 변환을 담당하는 컨버터 클래스 */
@Component
public class FooConverter
		implements AbstractDomainConverter<SampleEntity, Foo, FooUseCaseResponse> {

	@Override
	public Foo from(SampleEntity source) {
		return Foo.builder().name(source.getName()).build();
	}

	public FooUseCaseResponse to(Foo source) {
		return FooUseCaseResponse.builder().name(source.getName()).build();
	}
}
