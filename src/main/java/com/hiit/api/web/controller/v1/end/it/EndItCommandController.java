package com.hiit.api.web.controller.v1.end.it;

import com.hiit.api.domain.dto.request.end.DeleteEndItUseCaseRequest;
import com.hiit.api.domain.dto.request.end.EditEndItUseCaseRequest;
import com.hiit.api.domain.usecase.end.it.DeleteEndItUseCase;
import com.hiit.api.domain.usecase.end.it.EditEndItUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.request.end.it.DeleteEndItRequest;
import com.hiit.api.web.dto.request.end.it.EditEndItRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/end/its")
@RequiredArgsConstructor
public class EndItCommandController {

	private final DeleteEndItUseCase deleteEndItUseCase;
	private final EditEndItUseCase editEndItUseCase;

	@PutMapping()
	public ApiResponse<ApiResponse.Success> editEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody EditEndItRequest request) {
		Long memberId = Long.valueOf(userDetails.getUsername());
		EditEndItUseCaseRequest editRequest =
				EditEndItUseCaseRequest.builder()
						.memberId(memberId)
						.endInItId(request.getId())
						.title(request.getTitle())
						.build();
		editEndItUseCase.execute(editRequest);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_UPDATED);
	}

	@DeleteMapping()
	public ApiResponse<ApiResponse.Success> deleteEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody DeleteEndItRequest request) {
		Long memberId = Long.valueOf(userDetails.getUsername());
		DeleteEndItUseCaseRequest deleteRequest =
				DeleteEndItUseCaseRequest.builder().memberId(memberId).endInItId(request.getId()).build();
		deleteEndItUseCase.execute(deleteRequest);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_DELETED);
	}
}
