package com.hiit.api.web.controller.v1.it;

import com.hiit.api.domain.dto.request.it.GetInItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetInItsUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetItUseCaseRequest;
import com.hiit.api.domain.dto.request.it.GetItsUseCaseRequest;
import com.hiit.api.domain.dto.request.it.InItMotivationUseCaseRequest;
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.usecase.it.GetInItUseCase;
import com.hiit.api.domain.usecase.it.GetInItsUseCase;
import com.hiit.api.domain.usecase.it.GetItUseCase;
import com.hiit.api.domain.usecase.it.GetItsUseCase;
import com.hiit.api.domain.usecase.it.InItMotivationUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.support.ApiResponse;
import com.hiit.api.support.ApiResponseGenerator;
import com.hiit.api.support.MessageCode;
import com.hiit.api.web.dto.validator.DataId;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/its")
@RequiredArgsConstructor
public class ItQueryController {

	private final GetItUseCase getItUseCase;
	private final GetItsUseCase getItsUseCase;
	private final GetInItUseCase getInItUseCase;
	private final GetInItsUseCase getInItsUseCase;
	private final InItMotivationUseCase inItMotivationUseCase;

	@GetMapping("{id}")
	public ApiResponse<ApiResponse.SuccessBody<ItInfo>> browseIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		ItInfo res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetItUseCaseRequest request =
					GetItUseCaseRequest.builder().itId(id).memberId(memberId).build();
			res = getItUseCase.execute(request);
			if (res == null) {
				res = getItInfoMockResponse();
			}
		} catch (Exception e) {
			res = getItInfoMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private ItInfo getItInfoMockResponse() {
		return ItInfo.builder()
				.id(1L)
				.topic("잇 주제")
				.startTime(LocalTime.of(1, 0))
				.endTime(LocalTime.of(2, 0))
				.inMemberCount(10L)
				.memberIn(true)
				.type(ItTypeDetails.IT_REGISTERED.getValue())
				.build();
	}

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ItInfos>> readIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		ItInfos res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetItsUseCaseRequest request = GetItsUseCaseRequest.builder().memberId(memberId).build();
			res = getItsUseCase.execute(request);
			if (res.getIts().isEmpty()) {
				res = getItInfosMockResponse();
			}
		} catch (Exception e) {
			res = getItInfosMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private ItInfos getItInfosMockResponse() {
		ItInfo it1 =
				ItInfo.builder()
						.id(1L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(true)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		ItInfo it2 =
				ItInfo.builder()
						.id(2L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(true)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		return new ItInfos(List.of(it1, it2));
	}

	@GetMapping("/ins")
	public ApiResponse<ApiResponse.SuccessBody<InItInfos>> readInIts(
			@AuthenticationPrincipal TokenUserDetails userDetails) {
		InItInfos res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetInItsUseCaseRequest request = GetInItsUseCaseRequest.builder().memberId(memberId).build();
			res = getInItsUseCase.execute(request);
			if (res.getItInInfos().isEmpty()) {
				res = getInItInfosMockResponse();
			}
		} catch (Exception e) {
			res = getInItInfosMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private InItInfos getInItInfosMockResponse() {
		InItInfo inIt1 =
				InItInfo.builder()
						.id(1L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.days(Long.toBinaryString(000001L))
						.inMemberCount(10L)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		InItInfo inIt2 =
				InItInfo.builder()
						.id(2L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.days(Long.toBinaryString(000001L))
						.inMemberCount(10L)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		return new InItInfos(List.of(inIt1, inIt2));
	}

	@GetMapping("/ins/{id}")
	public ApiResponse<ApiResponse.SuccessBody<InItInfo>> browseInIt(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		InItInfo res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			GetInItUseCaseRequest request =
					GetInItUseCaseRequest.builder().inIt(id).memberId(memberId).build();
			res = getInItUseCase.execute(request);
			if (res == null) {
				res = getInItInfoMockResponse();
			}
		} catch (Exception e) {
			res = getInItInfoMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private InItInfo getInItInfoMockResponse() {
		return InItInfo.builder()
				.id(1L)
				.title("참여 잇 제목")
				.topic("참여 잇 주제")
				.startTime(LocalTime.of(7, 0))
				.endTime(LocalTime.of(9, 0))
				.days(Long.toBinaryString(000001L))
				.inMemberCount(10L)
				.type(ItTypeDetails.IT_REGISTERED.getValue())
				.build();
	}

	@GetMapping("/ins/{id}/motivations")
	public ApiResponse<ApiResponse.SuccessBody<ItMotivations>> readItMotivations(
			@AuthenticationPrincipal TokenUserDetails userDetails, @PathVariable @DataId Long id) {
		ItMotivations res = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			InItMotivationUseCaseRequest request =
					InItMotivationUseCaseRequest.builder().inItId(id).memberId(memberId).build();
			res = inItMotivationUseCase.execute(request);
			if (res == null) {
				res = getItMotivationsMockResponse();
			}
		} catch (Exception e) {
			res = getItMotivationsMockResponse();
		}
		return ApiResponseGenerator.success(res, HttpStatus.OK, MessageCode.SUCCESS);
	}

	private ItMotivations getItMotivationsMockResponse() {
		List<String> motivations = List.of("motivation1", "motivation2");
		return new ItMotivations(motivations);
	}
}
