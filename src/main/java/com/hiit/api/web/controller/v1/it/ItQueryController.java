package com.hiit.api.web.controller.v1.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetItsUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.domain.usecase.in.GetItUseCase;
import com.hiit.api.domain.usecase.in.GetItsUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import java.time.LocalTime;
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
public class ItQueryController {

	private final GetItUseCase getItUseCase;
	private final GetItsUseCase getItsUseCase;

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<ItInfo>> browseIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		Long memberId = Long.valueOf(userDetails.getUsername());
		GetItUseCaseRequest request = GetItUseCaseRequest.builder().itId(id).memberId(memberId).build();

		ItInfo res = null;
		try {
			res = getItUseCase.execute(request);
			if (res == null) {
				res = getItInfoMockResponse();
			}
		} catch (Exception e) {
			res = getItInfoMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private ItInfo getItInfoMockResponse() {
		return ItInfo.builder()
				.id(1L)
				.topic("잇 주제")
				.startTime(LocalTime.of(1, 0))
				.endTime(LocalTime.of(2, 0))
				.inMemberCount(10L)
				.memberIn(true)
				.build();
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		Long memberId = Long.valueOf(userDetails.getUsername());
		GetItsUseCaseRequest request = GetItsUseCaseRequest.builder().memberId(memberId).build();
		ItInfos res = null;
		try {
			res = getItsUseCase.execute(request);
			if (res.getIts().isEmpty()) {
				res = getItInfosMockResponse();
			}
		} catch (Exception e) {
			res = getItInfosMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private ItInfos getItInfosMockResponse() {
		ItInfo it1 =
				ItInfo.builder()
						.id(1L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(true)
						.build();
		ItInfo it2 =
				ItInfo.builder()
						.id(2L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(true)
						.build();
		return new ItInfos(List.of(it1, it2));
	}

	@GetMapping("/ins")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readInIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		InItInfo inIt1 =
				InItInfo.builder()
						.id(1L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.days(Long.toBinaryString(000001L))
						.inMemberCount(10L)
						.build();
		InItInfo inIt2 =
				InItInfo.builder()
						.id(2L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.days(Long.toBinaryString(000001L))
						.inMemberCount(10L)
						.build();
		ServiceResponse res = new InItInfos(List.of(inIt1, inIt2));
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
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
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
