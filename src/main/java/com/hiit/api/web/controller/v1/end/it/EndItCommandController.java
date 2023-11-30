package com.hiit.api.web.controller.v1.end.it;

import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.request.end.it.DeleteEndItRequest;
import com.hiit.api.web.dto.request.end.it.EditEndItRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
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

	@PutMapping()
	public ApiResponse<ApiResponse.Success> editEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody EditEndItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_UPDATED);
	}

	@DeleteMapping()
	public ApiResponse<ApiResponse.Success> deleteEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody DeleteEndItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_DELETED);
	}
}
