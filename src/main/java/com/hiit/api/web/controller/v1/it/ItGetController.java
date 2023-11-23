package com.hiit.api.web.controller.v1.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/its")
@RequiredArgsConstructor
public class ItGetController {

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		ItInfo it =
				ItInfo.builder()
						.id(1L)
						.topic("잇 주제")
						.startTime(1L)
						.endTime(2L)
						.inMemberCount(10L)
						.memberIn(true)
						.build();
		ServiceResponse res = it;
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		ItInfo it =
				ItInfo.builder()
						.id(1L)
						.topic("잇 주제")
						.startTime(1L)
						.endTime(2L)
						.inMemberCount(10L)
						.memberIn(true)
						.build();
		ServiceResponse res = new ItInfos(List.of(it));
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/ins")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readInIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		InItInfo inIt =
				InItInfo.builder()
						.id(1L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(7L)
						.endTime(9L)
						.days(Long.toBinaryString(000001L))
						.inMemberCount(10L)
						.build();
		ServiceResponse res = new InItInfos(List.of(inIt));
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/ins/{id}")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		InItInfo inIt =
				InItInfo.builder()
						.id(1L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(7L)
						.endTime(9L)
						.days(Long.toBinaryString(000001L))
						.inMemberCount(10L)
						.build();
		ServiceResponse res = inIt;
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/ins/{id}/motivations")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readItMotivations(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		List<String> motivations = List.of("motivation1", "motivation2");
		ServiceResponse res = new ItMotivations(motivations);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
