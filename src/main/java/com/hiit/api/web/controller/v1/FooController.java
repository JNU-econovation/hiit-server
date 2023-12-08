package com.hiit.api.web.controller.v1;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.usecase.foo.SaveFooUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.request.SaveFooRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 도메인 관련 요청을 처리하는 컨트롤러 클래스<br>
 * 컨트롤러는 요청을 통해 <b>"어떤 도메인"</b>를 어떠한 요청을 하고 싶은가에 주목한다.<br>
 * 따라서 네이밍은 <b>"(도메인) + 요청"</b>으로 한다.
 */
@RestController
@RequestMapping("/api/v1/foo")
@RequiredArgsConstructor
public class FooController {

	/**
	 * 요청을 처리하는 유즈케이스 클래스<br>
	 * 하나의 요청에 하나의 유즈케이스 사용하는 것을 권장한다.
	 */
	private final SaveFooUseCase saveFooUseCase;

	/**
	 * 저장 요청을 처리하는 메서드
	 *
	 * @param request 저장 요청
	 * @return 저장 응답
	 */
	@PostMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> save(
			@RequestBody SaveFooRequest request, @AuthenticationPrincipal TokenUserDetails userDetails) {
		ServiceResponse response = saveFooUseCase.execute(request);
		return ApiResponseGenerator.success(response, HttpStatus.OK);
	}
}
