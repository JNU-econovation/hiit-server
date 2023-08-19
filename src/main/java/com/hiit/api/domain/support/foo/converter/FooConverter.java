package com.hiit.api.domain.support.foo.converter;

import com.hiit.api.common.support.converter.AbstractDtoConverter;
import com.hiit.api.domain.dto.response.FooUseCaseResponse;
import com.hiit.api.domain.model.Foo;
import com.hiit.api.repository.entity.business.FooEntity;
import com.hiit.api.web.dto.request.SaveFooRequest;
import org.springframework.stereotype.Component;

/** 유즈케이스 계층에서 사용하는 도메인 객체 관련 변환을 담당하는 컨버터 클래스 */
@Component
public class FooConverter implements AbstractDtoConverter<SaveFooRequest, Foo> {

	@Override
	public Foo from(SaveFooRequest source) {
		return Foo.builder().name(source.getName()).count(source.getCount()).build();
	}

	/**
	 * 다른 계층으로의 응답을 위한 변환 메서드
	 *
	 * @param source 도메인 객체
	 * @return 다른 계층으로의 응답
	 */
	public FooUseCaseResponse toDomainResponse(Foo source) {
		return FooUseCaseResponse.builder().name(source.getName()).build();
	}

	public FooEntity toEntity(Foo source) {
		return FooEntity.builder().name(source.getName()).count(source.getCount()).build();
	}
}
