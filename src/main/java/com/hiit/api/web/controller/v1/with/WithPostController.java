package com.hiit.api.web.controller.v1.with;

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
public class WithPostController {

	@PostMapping()
	public ApiResponse<ApiResponse.Success> createWith(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody AddWithRequest request) {
		return ApiResponseGenerator.success(HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
	}

	@DeleteMapping()
	public ApiResponse<ApiResponse.Success> deleteWith(
			@Valid @RequestBody DeleteWithRequest request) {
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.SUCCESS);
	}
}
