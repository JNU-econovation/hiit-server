package com.hiit.api.web.controller.v1.member;

import com.hiit.api.web.dto.request.noti.MemberItNotiRequest;
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
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberPostController {

	@PostMapping("/{mid}/noti/its")
	public ApiResponse<ApiResponse.SuccessBody<Void>> noti(
			@PathVariable Long mid, @Valid @RequestBody MemberItNotiRequest request) {
		return ApiResponseGenerator.success(HttpStatus.CREATED);
	}
}
