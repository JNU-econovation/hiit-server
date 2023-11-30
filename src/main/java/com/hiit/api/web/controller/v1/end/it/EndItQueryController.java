package com.hiit.api.web.controller.v1.end.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithInfo;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithMemberInfo;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/end/its")
@RequiredArgsConstructor
public class EndItQueryController {

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		EndItInfo endIt =
				EndItInfo.builder()
						.id(1L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(7L)
						.endTime(9L)
						.startDate(new Date())
						.endDate(new Date())
						.withCount(10L)
						.build();
		ServiceResponse res = endIt;
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readEndIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		EndItInfo endIt1 =
				EndItInfo.builder()
						.id(1L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(1L)
						.endTime(2L)
						.startDate(new Date())
						.endDate(new Date())
						.withCount(10L)
						.build();
		EndItInfo endIt2 =
				EndItInfo.builder()
						.id(2L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(1L)
						.endTime(2L)
						.startDate(new Date())
						.endDate(new Date())
						.withCount(10L)
						.build();

		ServiceResponse res = new EndItInfos(List.of(endIt1, endIt2));
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/withs")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseEndWith(
			@AuthenticationPrincipal TokenUserDetails userDetails, @RequestParam @DataId Long id) {
		EndWithMemberInfo withMemberInfo =
				EndWithMemberInfo.builder().id(1L).profile("프로필 사진").name("이름").resolution("잇 다짐").build();
		EndWithInfo withInfo1 =
				EndWithInfo.builder()
						.id(1L)
						.content("윗 내용")
						.hit(10L)
						.withMemberInfo(withMemberInfo)
						.build();
		EndWithInfo withInfo2 =
				EndWithInfo.builder()
						.id(2L)
						.content("윗 내용")
						.hit(10L)
						.withMemberInfo(withMemberInfo)
						.build();
		ServiceResponse res = new EndWithInfos(List.of(withInfo1, withInfo2));
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
