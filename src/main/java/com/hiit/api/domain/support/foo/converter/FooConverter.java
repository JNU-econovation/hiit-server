package com.hiit.api.domain.support.foo.converter;

import com.hiit.api.domain.dao.foo.FooData;
import com.hiit.api.domain.dto.foo.response.FooUseCaseResponse;
import com.hiit.api.domain.model.foo.Foo;
import com.hiit.api.domain.support.AbstractDomainConverter;
import org.springframework.stereotype.Component;

/** 유즈케이스 계층에서 사용하는 도메인 객체 관련 변환을 담당하는 컨버터 클래스 */
@Component
public class FooConverter implements AbstractDomainConverter<FooData, Foo, FooUseCaseResponse> {

	@Override
	public Foo from(FooData source) {
		return Foo.builder().name(source.getName()).build();
	}

	public FooUseCaseResponse to(Foo source) {
		return FooUseCaseResponse.builder().name(source.getName()).build();
	}
}
