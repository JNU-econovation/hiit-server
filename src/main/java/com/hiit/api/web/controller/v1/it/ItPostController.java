package com.hiit.api.web.controller.v1.it;

import com.hiit.api.web.dto.request.it.InItRequest;
import com.hiit.api.web.dto.request.it.ParticipateTogetherRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/its")
@RequiredArgsConstructor
public class ItPostController {

	@PostMapping("/{id}/in")
	public ApiResponse<ApiResponse.SuccessBody<Void>> inIt(
			@PathVariable Long id, @Valid @RequestBody InItRequest request) {
		return ApiResponseGenerator.success(HttpStatus.CREATED);
	}

	@PostMapping("/{id}/together")
	public ApiResponse<ApiResponse.SuccessBody<Void>> participateTogether(
			@PathVariable Long id, @Valid @RequestBody ParticipateTogetherRequest request) {
		return ApiResponseGenerator.success(HttpStatus.CREATED);
	}
}
