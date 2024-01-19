package com.hiit.api.web.controller.v1.member;

import com.hiit.api.domain.dto.request.member.GetMemberInfoUseCaseRequest;
import com.hiit.api.domain.dto.request.member.GetMemberItInfoUseCaseRequest;
import com.hiit.api.domain.dto.response.member.MemberInfo;
import com.hiit.api.domain.dto.response.member.MemberItInfo;
import com.hiit.api.domain.usecase.member.GetMemberInfoUseCase;
import com.hiit.api.domain.usecase.member.GetMemberItInfoUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.validator.DataId;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberQueryController {

	private final GetMemberInfoUseCase getMemberInfoUseCase;
	private final GetMemberItInfoUseCase getMemberItInfoUseCase;

	@GetMapping("/{id}")
	public ApiResponse<ApiResponse.SuccessBody<MemberInfo>> browseMember(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable("id") @DataId Long id) {
		Long requestId = null;
		if (Objects.equals(id, 0L)) {
			requestId = Long.valueOf(userDetails.getUsername());
		} else {
			requestId = id;
		}
		GetMemberInfoUseCaseRequest request =
				GetMemberInfoUseCaseRequest.builder().memberId(requestId).build();
		MemberInfo res = getMemberInfoUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@GetMapping("/stats/it")
	public ApiResponse<ApiResponse.SuccessBody<MemberItInfo>> browseItStat(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@RequestParam @DataId Long id,
			@RequestParam @DataId Long iid) {
		GetMemberItInfoUseCaseRequest request =
				GetMemberItInfoUseCaseRequest.builder().memberId(id).itId(iid).build();
		MemberItInfo res = getMemberItInfoUseCase.execute(request);
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}
}
