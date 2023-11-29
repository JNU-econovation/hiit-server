package com.hiit.api.web.controller.v1;

import com.hiit.api.common.exception.ExceptionSpec;
import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.common.support.DayCodeSpec;
import com.hiit.api.common.support.token.AuthToken;
import com.hiit.api.common.support.token.TokenGenerator;
import com.hiit.api.domain.dto.response.Banners;
import com.hiit.api.domain.dto.response.NotiInfos;
import com.hiit.api.domain.dto.response.NoticeInfo;
import com.hiit.api.security.authentication.authority.Roles;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.request.SuggestItRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 별도의 계층으로 나눌 필요가 없는 API 컨트롤러 */
@Validated
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
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> notice() {
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

	@PostMapping("/suggest/its")
	public ApiResponse<ApiResponse.Success> suggestIts(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody SuggestItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_CREATED);
	}

	@GetMapping("/errors")
	public ApiResponse<ApiResponse.SuccessBody<ErrorCodeView>> errors() {
		List<ErrorCode> specs =
				Arrays.stream(ExceptionSpec.values())
						.map(e -> new ErrorCode(e.getSituation(), e.getHttpCode(), e.getCode()))
						.collect(Collectors.toList());
		ErrorCodeView res = new ErrorCodeView(specs);
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@Builder(toBuilder = true)
	static class ErrorCode {

		private final String situation;
		private final Integer httpCode;
		private final String code;
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@Builder(toBuilder = true)
	static class ErrorCodeView {

		private final List<ErrorCode> errors;
	}

	@GetMapping("/dayCode")
	public ApiResponse<ApiResponse.SuccessBody<DayCodeView>> dayCode() {
		List<DayCode> days =
				Arrays.stream(DayCodeSpec.values())
						.map(e -> new DayCode(e.getCode(), e.getDays()))
						.collect(Collectors.toList());
		DayCodeView res = new DayCodeView(days);
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@Builder(toBuilder = true)
	static class DayCode {
		private final String code;
		private final String day;
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@Builder(toBuilder = true)
	static class DayCodeView {
		private final List<DayCode> days;
	}

	// todo for mock server & delete after development
	private final TokenGenerator tokenGenerator;

	@GetMapping("/token")
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> token() {
		AuthToken res = tokenGenerator.generateAuthToken(1L, List.of(Roles.ROLE_USER));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}
}
