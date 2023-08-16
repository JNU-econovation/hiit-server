package com.hiit.api.domain.usecase.foo;

import com.hiit.api.domain.dto.response.FooUseCaseResponse;
import com.hiit.api.domain.model.Foo;
import com.hiit.api.domain.service.foo.FooService;
import com.hiit.api.domain.support.foo.converter.FooConverter;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.web.dto.request.SaveFooRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 컨트롤러의 요청을 처리하는 유즈케이스 클래스<br>
 * 유즈케이스는 도메인을 <b>"어떻게"</b>하고 싶은가에 주목한다.<br>
 * 따라서 네이밍은 <b>"어떻게 + 도메인"</b>으로 한다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SaveFooUseCase implements AbstractUseCase<SaveFooRequest, FooUseCaseResponse> {

	/** 다른 계층 혹은 다른 도메인과 연동이 필요한 경우 사용한다. */
	private final FooService fooService;

	private final FooConverter fooConverter;

	@Override
	public FooUseCaseResponse execute(SaveFooRequest request) {
		Foo foo = fooConverter.from(request);

		fooService.doA(foo);
		fooService.doB(foo);
		fooService.doC(foo);

		return fooConverter.toDomainResponse(foo);
	}
}