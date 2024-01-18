package com.hiit.api.web.controller.v1.it;

import com.hiit.api.domain.dto.request.it.GetInItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetInItsUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetItsUseCaseRequest;
import com.hiit.api.domain.dto.request.it.InItMotivationUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.domain.usecase.it.GetInItUseCase;
import com.hiit.api.domain.usecase.it.GetInItsUseCase;
import com.hiit.api.domain.usecase.it.GetItUseCase;
import com.hiit.api.domain.usecase.it.GetItsUseCase;
import com.hiit.api.domain.usecase.it.InItMotivationUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.validator.DataId;
import java.util.Objects;
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
	private final GetInItUseCase getInItUseCase;
	private final GetInItsUseCase getInItsUseCase;
	private final InItMotivationUseCase inItMotivationUseCase;

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<ItInfo>> browseIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetItUseCaseRequest request = GetItUseCaseRequest.builder().itId(id).memberId(memberId).build();
		ItInfo res = getItUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ItInfos>> readIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetItsUseCaseRequest request = GetItsUseCaseRequest.builder().memberId(memberId).build();
		ItInfos res = getItsUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/ins")
	public ApiResponse<ApiResponse.SuccessBody<InItInfos>> readInIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetInItsUseCaseRequest request = GetInItsUseCaseRequest.builder().memberId(memberId).build();
		InItInfos res = getInItsUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/ins/{id}")
	public ApiResponse<ApiResponse.SuccessBody<InItInfo>> browseInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetInItUseCaseRequest request =
				GetInItUseCaseRequest.builder().inIt(id).memberId(memberId).build();
		InItInfo res = getInItUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/ins/{id}/motivations")
	public ApiResponse<ApiResponse.SuccessBody<ItMotivations>> readItMotivations(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		InItMotivationUseCaseRequest request =
				InItMotivationUseCaseRequest.builder().inItId(id).memberId(memberId).build();
		ItMotivations res = inItMotivationUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
