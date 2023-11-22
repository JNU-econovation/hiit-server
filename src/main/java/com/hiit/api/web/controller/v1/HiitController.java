package com.hiit.api.web.controller.v1;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.common.support.token.AuthToken;
import com.hiit.api.common.support.token.TokenGenerator;
import com.hiit.api.domain.dto.response.Banners;
import com.hiit.api.domain.dto.response.NotiInfos;
import com.hiit.api.domain.dto.response.NoticeInfo;
import com.hiit.api.security.authentication.authority.Roles;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import java.util.Date;
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
		Banners res = Banners.builder().size(1L).urls(List.of("배너 이미지 주소")).build();
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/notice")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> noti() {
		NoticeInfo notice =
				NoticeInfo.builder()
						.id(1L)
						.date(new Date())
						.type("공지 타입")
						.title("공지 제목")
						.content("공지 내용")
						.build();
		NotiInfos res = new NotiInfos(List.of(notice));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	// todo for mock server & delete after development
	private final TokenGenerator tokenGenerator;

	@GetMapping("/token")
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> token() {
		AuthToken res = tokenGenerator.generateAuthToken(1L, List.of(Roles.USER));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}
}
