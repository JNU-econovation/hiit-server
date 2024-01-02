package com.hiit.api.web.controller.v1.it;

import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.request.it.AddInItRequest;
import com.hiit.api.web.dto.request.it.DeleteInItRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/its")
@RequiredArgsConstructor
public class ItCommandController {

	@PostMapping("/ins")
	public ApiResponse<ApiResponse.Success> createInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody AddInItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
	}

	@PutMapping("/ins")
	public ApiResponse<ApiResponse.Success> editInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody AddInItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_UPDATED);
	}

	@DeleteMapping("/ins")
	public ApiResponse<ApiResponse.Success> deleteInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody DeleteInItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_DELETED);
	}
}
