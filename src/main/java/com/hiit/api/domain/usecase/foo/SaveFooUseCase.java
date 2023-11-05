package com.hiit.api.domain.usecase.foo;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dao.foo.FooDao;
import com.hiit.api.domain.dao.foo.FooData;
import com.hiit.api.domain.model.foo.Foo;
import com.hiit.api.domain.service.foo.FooService;
import com.hiit.api.domain.support.foo.converter.FooConverter;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.web.dto.request.SaveFooRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 컨트롤러의 요청을 처리하는 유즈케이스 클래스<br>
 * 유즈케이스는 도메인을 <b>"어떻게"</b>하고 싶은가에 주목한다.<br>
 * 따라서 네이밍은 <b>"어떻게 + 도메인"</b>으로 한다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SaveFooUseCase implements AbstractUseCase<SaveFooRequest> {

	private final FooDao fooDao;

	/** 다른 계층 혹은 다른 도메인과 연동이 필요한 경우 사용한다. */
	private final FooService fooService;

	private final FooConverter fooConverter;

	@Override
	@Transactional
	public ServiceResponse execute(SaveFooRequest request) {

		// save for foo
		FooData saved = fooDao.save(FooData.builder().name("name").build());

		FooData data =
				fooDao.findById(saved.getId()).orElseThrow(() -> new RuntimeException("not found"));

		Foo foo = fooConverter.from(data);

		fooService.doA(foo);
		fooService.doB(foo);
		fooService.doC(foo);

		return fooConverter.to(foo);
	}
}
