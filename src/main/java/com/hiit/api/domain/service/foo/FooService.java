package com.hiit.api.domain.service.foo;

import com.hiit.api.domain.model.foo.Foo;
import org.springframework.stereotype.Component;

/**
 * 도메인을 처리하기 위해 유즈케이스 내부에서 사용하는 서비스 클래스<br>
 * 도메인과 관련해 다른 계층과 연동하는 경우에 사용한다.<br>
 */
@Component
public class FooService {

	public String doA(Foo foo) {
		return "A";
	}

	public String doB(Foo foo) {
		return "B";
	}

	public String doC(Foo foo) {
		return "C";
	}
}
