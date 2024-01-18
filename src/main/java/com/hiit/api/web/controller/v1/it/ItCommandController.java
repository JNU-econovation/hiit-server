package com.hiit.api.web.controller.v1.it;

import com.hiit.api.domain.dto.request.it.CreateInItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.DeleteInItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.EditInItUseCaseRequest;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.usecase.it.CreateInItUseCase;
import com.hiit.api.domain.usecase.it.DeleteInItUseCase;
import com.hiit.api.domain.usecase.it.EditInItUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.request.it.AddInItRequest;
import com.hiit.api.web.dto.request.it.DeleteInItRequest;
import com.hiit.api.web.dto.request.it.EditInItRequest;
import java.util.Objects;
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

	private final CreateInItUseCase createInItUseCase;
	private final EditInItUseCase editInItUseCase;
	private final DeleteInItUseCase deleteInItUseCase;

	@PostMapping("/ins")
	public ApiResponse<ApiResponse.Success> createInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody AddInItRequest request) {
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			CreateInItUseCaseRequest useCaseRequest =
					CreateInItUseCaseRequest.builder()
							.memberId(memberId)
							.itId(request.getId())
							.dayCode(request.getDayCode())
							.resolution(request.getResolution())
							.type(ItTypeDetails.of(request.getType().getValue()))
							.build();
			createInItUseCase.execute(useCaseRequest);
			return ApiResponseGenerator.success(HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
		} catch (Exception e) {
			return ApiResponseGenerator.success(HttpStatus.CREATED, MessageCode.RESOURCE_CREATED);
		}
	}

	@PutMapping("/ins")
	public ApiResponse<ApiResponse.Success> editInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody EditInItRequest request) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		EditInItUseCaseRequest useCaseRequest =
				EditInItUseCaseRequest.builder()
						.memberId(memberId)
						.inIt(request.getId())
						.dayCode(request.getDayCode())
						.resolution(request.getResolution())
						.build();
		editInItUseCase.execute(useCaseRequest);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_UPDATED);
	}

	@DeleteMapping("/ins")
	public ApiResponse<ApiResponse.Success> deleteInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@Valid @RequestBody DeleteInItRequest request) {
		Long memberId = null;
		if (Objects.isNull(userDetails)) {
			memberId = 1L;
		} else {
			memberId = Long.valueOf(userDetails.getUsername());
		}
		DeleteInItUseCaseRequest useCaseRequest =
				DeleteInItUseCaseRequest.builder()
						.memberId(memberId)
						.inItId(request.getId())
						.endTitle(request.getEndTitle())
						.build();
		deleteInItUseCase.execute(useCaseRequest);
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_DELETED);
	}
}
