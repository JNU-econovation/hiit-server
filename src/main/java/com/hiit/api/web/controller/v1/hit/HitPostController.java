package com.hiit.api.web.controller.v1.hit;

import com.hiit.api.web.dto.request.it.HitRequest;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hit")
@RequiredArgsConstructor
public class HitPostController {
	@PostMapping()
	public ApiResponse<ApiResponse.SuccessBody<Void>> hit(@Valid @RequestBody HitRequest request) {
		return ApiResponseGenerator.success(HttpStatus.CREATED);
	}
}
