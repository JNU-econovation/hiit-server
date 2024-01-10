package com.hiit.api.web.controller.v1.member;

import com.hiit.api.common.token.AuthToken;
import com.hiit.api.domain.dto.request.member.CreateSocialMemberUseCaseRequest;
import com.hiit.api.domain.dto.response.member.UserAuthToken;
import com.hiit.api.domain.model.member.CertificationSubjectInfo;
import com.hiit.api.domain.usecase.member.FacadeCreateMemberUseCase;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.request.member.CreateSocialMemberRequest;
import com.hiit.api.web.dto.request.member.TokenRefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberCommandController {

	private final FacadeCreateMemberUseCase facadeCreateMemberUseCase;

	@PostMapping()
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> save(
			@RequestBody CreateSocialMemberRequest request) {
		CreateSocialMemberUseCaseRequest useCaseRequest =
				CreateSocialMemberUseCaseRequest.builder()
						.code(request.getCode())
						.certificationSubjectInfo(
								CertificationSubjectInfo.valueOf(request.getSocialSubject().name()))
						.build();
		AuthToken res = null;
		try {
			res = facadeCreateMemberUseCase.execute(useCaseRequest);
		} catch (Exception e) {
			res = getUserAuthTokenMockResponse();
			return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	@PostMapping("/token")
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> token(
			@RequestBody TokenRefreshRequest request) {
		AuthToken res = getUserAuthTokenMockResponse();
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private UserAuthToken getUserAuthTokenMockResponse() {
		return UserAuthToken.builder().accessToken("accessToken").refreshToken("refreshToken").build();
	}
}
