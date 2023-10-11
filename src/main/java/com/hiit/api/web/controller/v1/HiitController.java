package com.hiit.api.web.controller.v1;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.common.support.token.AuthToken;
import com.hiit.api.common.support.token.TokenGenerator;
import com.hiit.api.domain.dto.response.noti.NotiResponse;
import com.hiit.api.domain.dto.response.noti.list.NotiResponses;
import com.hiit.api.domain.dto.response.support.Banners;
import com.hiit.api.security.authentication.authority.Roles;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 별도의 계층으로 나눌 필요가 없는 API 컨트롤러 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HiitController {

	@GetMapping("/banners")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> banners() {
		Banners res = Banners.builder().size(1L).urls(List.of("url1")).build();
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/noti")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> noti() {
		NotiResponse noti =
				NotiResponse.builder()
						.id(1L)
						.date(LocalDate.now())
						.type("type")
						.title("title")
						.content("content")
						.build();
		NotiResponses res = new NotiResponses(List.of(noti));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	// todo for mock server
	// todo delete
	private final TokenGenerator tokenGenerator;

	@GetMapping("/token")
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> token() {
		AuthToken res = tokenGenerator.generateAuthToken(1L, List.of(Roles.USER));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}
}
