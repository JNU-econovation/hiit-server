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
		MemberInfo res = null;
		GetMemberInfoUseCaseRequest request =
				GetMemberInfoUseCaseRequest.builder().memberId(id).build();
		try {
			res = getMemberInfoUseCase.execute(request);
			if (Objects.isNull(res)) {
				return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
			}
			return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
		} catch (Exception e) {
			res = getMemberInfoMockResponse();
			return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
		}
	}

	private MemberInfo getMemberInfoMockResponse() {
		return MemberInfo.builder().id(1L).name("멤버 이름").profile("멤버 프로필").inItCount(10L).build();
	}

	@GetMapping("/stats/it")
	public ApiResponse<ApiResponse.SuccessBody<MemberItInfo>> browseItStat(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@RequestParam @DataId Long id,
			@RequestParam @DataId Long iid) {
		GetMemberItInfoUseCaseRequest request =
				GetMemberItInfoUseCaseRequest.builder().memberId(id).itId(iid).build();
		MemberItInfo res = null;
		try {
			res = getMemberItInfoUseCase.execute(request);
		} catch (Exception e) {
			res = getMemberItInfoMockResponse();
			return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private MemberItInfo getMemberItInfoMockResponse() {
		return MemberItInfo.builder().id(1L).name("멤버 이름").profile("멤버 프로필").withCount(10L).build();
	}
}
