package com.hiit.api.web.controller.v1.with;

import com.hiit.api.domain.dto.request.with.CreateWithUseCaseRequest;
import com.hiit.api.domain.dto.request.with.DeleteWithUseCaseRequest;
import com.hiit.api.domain.usecase.with.CreateWithUseCase;
import com.hiit.api.domain.usecase.with.DeleteWithUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.request.with.AddWithRequest;
import com.hiit.api.web.dto.request.with.DeleteWithRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/its/withs")
@RequiredArgsConstructor
public class WithCommandController {

	private final CreateWithUseCase createWithUseCase;
	private final DeleteWithUseCase deleteWithUseCase;

	@PostMapping()
	public ApiResponse<ApiResponse.Success> createWith(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody AddWithRequest request) {
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			CreateWithUseCaseRequest createWithRequest =
					CreateWithUseCaseRequest.builder()
							.memberId(memberId)
							.inItId(request.getId())
							.content(request.getContent())
							.build();
			createWithUseCase.execute(createWithRequest);
			return ApiResponseGenerator.success(HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
		} catch (Exception e) {
			return ApiResponseGenerator.success(HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
		}
	}

	@DeleteMapping()
	public ApiResponse<ApiResponse.Success> deleteWith(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody DeleteWithRequest request) {
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			DeleteWithUseCaseRequest deleteWithRequest =
					DeleteWithUseCaseRequest.builder().memberId(memberId).withId(request.getId()).build();
			deleteWithUseCase.execute(deleteWithRequest);
			return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_DELETED);
		} catch (Exception e) {
			return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_DELETED);
		}
	}
}
