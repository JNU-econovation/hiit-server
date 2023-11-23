package com.hiit.api.web.controller.v1.hit;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.hit.HitInfo;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.request.hit.HitRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/its/withs/hits")
@RequiredArgsConstructor
public class HitPostController {

	@PostMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> hit(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody HitRequest request) {
		HitInfo hit = HitInfo.builder().count(100L).build();
		return ApiResponseGenerator.success(hit, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
