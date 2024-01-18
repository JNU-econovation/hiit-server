package com.hiit.api.web.controller.v1.hit;

import com.hiit.api.domain.dto.request.hit.HitUseCaseRequest;
import com.hiit.api.domain.dto.response.hit.HitInfo;
import com.hiit.api.domain.usecase.hit.HitUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.request.hit.HitRequest;
import java.util.Objects;
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
public class HitCommandController {

	private final HitUseCase hitUseCase;

	@PostMapping()
	public ApiResponse<ApiResponse.SuccessBody<HitInfo>> hit(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody HitRequest request) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		HitUseCaseRequest hitRequest =
				HitUseCaseRequest.builder().memberId(memberId).withId(request.getId()).build();
		HitInfo hit = hitUseCase.execute(hitRequest);

		return ApiResponseGenerator.success(hit, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
