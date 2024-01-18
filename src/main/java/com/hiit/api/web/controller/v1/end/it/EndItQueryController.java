package com.hiit.api.web.controller.v1.end.it;

import com.hiit.api.domain.dto.request.end.GetEndItUseCaseRequest;
import com.hiit.api.domain.dto.request.end.GetEndItsUseCaseRequest;
import com.hiit.api.domain.dto.request.end.GetEndWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.usecase.end.it.GetEndItUseCase;
import com.hiit.api.domain.usecase.end.it.GetEndItsUseCase;
import com.hiit.api.domain.usecase.end.it.GetEndWithsUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/end/its")
@RequiredArgsConstructor
public class EndItQueryController {

	private final GetEndItUseCase getEndItUseCase;
	private final GetEndItsUseCase getEndItsUseCase;
	private final GetEndWithsUseCase getEndWithsUseCase;

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<EndItInfo>> browseEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetEndItUseCaseRequest request =
				GetEndItUseCaseRequest.builder().endInItId(id).memberId(memberId).build();
		EndItInfo res = getEndItUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<EndItInfos>> readEndIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetEndItsUseCaseRequest request = GetEndItsUseCaseRequest.builder().memberId(memberId).build();
		EndItInfos res = getEndItsUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/withs")
	public ApiResponse<ApiResponse.SuccessBody<EndWithInfos>> browseEndWith(
			@AuthenticationPrincipal TokenUserDetails userDetails, @RequestParam @DataId Long id) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		GetEndWithsUseCaseRequest request =
				GetEndWithsUseCaseRequest.builder().memberId(memberId).endInItId(id).build();
		EndWithInfos res = getEndWithsUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
