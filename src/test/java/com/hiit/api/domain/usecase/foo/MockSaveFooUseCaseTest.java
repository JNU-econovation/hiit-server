package com.hiit.api.domain.usecase.foo;

import static com.hiit.config.util.DtoArgumentMatcher.anyDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hiit.api.domain.model.foo.Foo;
import com.hiit.api.domain.service.foo.FooService;
import com.hiit.api.domain.support.foo.converter.FooConverter;
import com.hiit.api.web.dto.request.SaveFooRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {SaveFooUseCase.class})
class MockSaveFooUseCaseTest {

	@Spy @InjectMocks SaveFooUseCase mockSaveFooUseCase;
	@Mock FooService fooDoSomething;
	@Spy FooConverter fooConverter;

	@Test
	void execute() {

		// given
		SaveFooRequest request = SaveFooRequest.builder().name("name").build();
		doReturn("a").when(fooDoSomething).doA(any(Foo.class));
		doReturn("b").when(fooDoSomething).doB(any(Foo.class));
		doReturn("c").when(fooDoSomething).doC(any(Foo.class));

		// when
		mockSaveFooUseCase.execute(request);

		// then
		verify(mockSaveFooUseCase, times(1)).execute(anyDto(SaveFooRequest.class));
		verify(fooDoSomething, times(1)).doA(any(Foo.class));
		verify(fooDoSomething, times(1)).doB(any(Foo.class));
		verify(fooDoSomething, times(1)).doC(any(Foo.class));
	}
}
