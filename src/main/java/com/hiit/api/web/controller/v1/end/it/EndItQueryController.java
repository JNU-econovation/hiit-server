package com.hiit.api.web.controller.v1.end.it;

import com.hiit.api.domain.dto.request.end.GetEndItUseCaseRequest;
import com.hiit.api.domain.dto.request.end.GetEndItsUseCaseRequest;
import com.hiit.api.domain.dto.request.end.GetEndWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithInfo;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithMemberInfo;
import com.hiit.api.domain.usecase.end.it.GetEndItUseCase;
import com.hiit.api.domain.usecase.end.it.GetEndItsUseCase;
import com.hiit.api.domain.usecase.end.it.GetEndWithsUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.validator.DataId;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
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
@RequestMapping("/api/v1/end/its")
@RequiredArgsConstructor
public class EndItQueryController {

	private final GetEndItUseCase getEndItUseCase;
	private final GetEndItsUseCase getEndItsUseCase;
	private final GetEndWithsUseCase getEndWithsUseCase;

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<EndItInfo>> browseEndIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		EndItInfo res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetEndItUseCaseRequest request =
					GetEndItUseCaseRequest.builder().endInItId(id).memberId(memberId).build();
			res = getEndItUseCase.execute(request);
			if (res == null) {
				res = getEndItMockResponse();
			}
		} catch (Exception e) {
			res = getEndItMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private EndItInfo getEndItMockResponse() {
		return EndItInfo.builder()
				.id(1L)
				.title("종료 잇 제목")
				.topic("종료 잇 주제")
				.startTime(LocalTime.of(7, 0))
				.endTime(LocalTime.of(9, 0))
				.startDate(new Date())
				.endDate(new Date())
				.withCount(10L)
				.build();
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<EndItInfos>> readEndIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		EndItInfos res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetEndItsUseCaseRequest request =
					GetEndItsUseCaseRequest.builder().memberId(memberId).build();
			res = getEndItsUseCase.execute(request);
			if (res.getEndIts().isEmpty()) {
				res = getEndItInfosMockResponse();
			}
		} catch (Exception e) {
			res = getEndItInfosMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private EndItInfos getEndItInfosMockResponse() {
		EndItInfo endIt1 =
				EndItInfo.builder()
						.id(1L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.startDate(new Date())
						.endDate(new Date())
						.withCount(10L)
						.build();
		EndItInfo endIt2 =
				EndItInfo.builder()
						.id(2L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(LocalTime.of(10, 0))
						.endTime(LocalTime.of(12, 0))
						.startDate(new Date())
						.endDate(new Date())
						.withCount(10L)
						.build();

		return new EndItInfos(List.of(endIt1, endIt2));
	}

	@GetMapping("/withs")
	public ApiResponse<ApiResponse.SuccessBody<EndWithInfos>> browseEndWith(
			@AuthenticationPrincipal TokenUserDetails userDetails, @RequestParam @DataId Long id) {
		EndWithInfos res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetEndWithsUseCaseRequest request =
					GetEndWithsUseCaseRequest.builder().memberId(memberId).endInItId(id).build();
			res = getEndWithsUseCase.execute(request);
			if (res.getEndWithInfos().isEmpty()) {
				res = getEndWithInfosMockResponse();
			}
		} catch (Exception e) {
			res = getEndWithInfosMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private EndWithInfos getEndWithInfosMockResponse() {
		EndWithMemberInfo withMemberInfo =
				EndWithMemberInfo.builder().id(1L).profile("프로필 사진").name("이름").resolution("잇 다짐").build();
		EndWithInfo withInfo1 =
				EndWithInfo.builder()
						.id(1L)
						.content("윗 내용")
						.hit(10L)
						.withMemberInfo(withMemberInfo)
						.build();
		EndWithInfo withInfo2 =
				EndWithInfo.builder()
						.id(2L)
						.content("윗 내용")
						.hit(10L)
						.withMemberInfo(withMemberInfo)
						.build();
		return new EndWithInfos(List.of(withInfo1, withInfo2));
	}
}
