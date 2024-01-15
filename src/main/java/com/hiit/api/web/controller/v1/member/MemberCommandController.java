package com.hiit.api.web.controller.v1.member;

import com.hiit.api.common.token.AuthToken;
import com.hiit.api.domain.dto.request.member.ConsentNotificationUseCaseRequest;
import com.hiit.api.domain.dto.request.member.CreateSocialMemberUseCaseRequest;
import com.hiit.api.domain.dto.request.member.GetTokenUseCaseRequest;
import com.hiit.api.domain.dto.response.member.UserAuthToken;
import com.hiit.api.domain.model.member.CertificationSubjectDetails;
import com.hiit.api.domain.usecase.member.ConsentNotificationUseCase;
import com.hiit.api.domain.usecase.member.FacadeCreateMemberUseCase;
import com.hiit.api.domain.usecase.member.GetTokenUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.request.member.CreateSocialMemberRequest;
import com.hiit.api.web.dto.request.member.NotificationConsentRequest;
import com.hiit.api.web.dto.request.member.TokenRefreshRequest;
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
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberCommandController {

	private final FacadeCreateMemberUseCase facadeCreateMemberUseCase;
	private final GetTokenUseCase getTokenUseCase;
	private final ConsentNotificationUseCase consentNotificationUseCase;

	@PostMapping()
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> save(
			@RequestBody CreateSocialMemberRequest request) {
		CreateSocialMemberUseCaseRequest useCaseRequest =
				CreateSocialMemberUseCaseRequest.builder()
						.code(request.getCode())
						.certificationSubjectDetails(
								CertificationSubjectDetails.valueOf(request.getSocialSubject().name()))
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

	// todo fix
	@PostMapping("/token")
	public ApiResponse<ApiResponse.SuccessBody<AuthToken>> token(
			@RequestBody TokenRefreshRequest request) {
		GetTokenUseCaseRequest getTokenUseCaseRequest =
				GetTokenUseCaseRequest.builder().token(request.getToken()).build();
		AuthToken res = null;
		try {
			res = getTokenUseCase.execute(getTokenUseCaseRequest);
		} catch (Exception e) {
			res = getUserAuthTokenMockResponse();
			return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private UserAuthToken getUserAuthTokenMockResponse() {
		return UserAuthToken.builder().accessToken("accessToken").refreshToken("refreshToken").build();
	}

	@PostMapping("/notification")
	public ApiResponse<ApiResponse.Success> notificationConsent(
			@AuthenticationPrincipal TokenUserDetails userDetails,
			@RequestBody NotificationConsentRequest request) {
		try {
			Long memberId = Long.valueOf(userDetails.getId());
			ConsentNotificationUseCaseRequest getTokenUseCaseRequest =
					ConsentNotificationUseCaseRequest.builder()
							.device(request.getDevice())
							.memberId(memberId)
							.build();
			consentNotificationUseCase.execute(getTokenUseCaseRequest);
		} catch (Exception e) {
			return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_UPDATED);
		}
		return ApiResponseGenerator.success(HttpStatus.OK, MessageCode.RESOURCE_UPDATED);
	}
}
