package com.hiit.api.web.controller.v1.with;

import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.request.with.GetWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.usecase.with.GetWithsUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.support.usecase.PageRequestGenerator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/its/withs")
@RequiredArgsConstructor
public class WithQueryController {

	private final GetWithsUseCase getWithsUseCase;

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<WithPage>> readWiths(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam @DataId Long id,
			@RequestParam(defaultValue = "false", required = false) Boolean my,
			@RequestParam(defaultValue = "false", required = false) Boolean random) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		PageRequest pageRequest = PageRequestGenerator.generate(pageable);
		GetWithsUseCaseRequest request =
				GetWithsUseCaseRequest.builder()
						.memberId(memberId)
						.inItId(id)
						.isMember(my)
						.pageable(pageRequest)
						.random(random)
						.build();
		WithPage withPage = getWithsUseCase.execute(request);

		return ApiResponseGenerator.success(withPage, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
