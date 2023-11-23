package com.hiit.api.web.controller.v1.member;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.member.MemberInfo;
import com.hiit.api.domain.dto.response.member.MemberItInfo;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberGetController {
	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseMember(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		MemberInfo member =
				MemberInfo.builder().id(1L).name("멤버 이름").profile("멤버 프로필").inItCount(10L).build();
		ServiceResponse res = member;
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/stats/it")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseItStat(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@RequestParam Long id,
			@RequestParam Long iid) {
		MemberItInfo memberItInfo =
				MemberItInfo.builder().id(1L).name("멤버 이름").profile("멤버 프로필").withCount(10L).build();
		ServiceResponse res = memberItInfo;
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
