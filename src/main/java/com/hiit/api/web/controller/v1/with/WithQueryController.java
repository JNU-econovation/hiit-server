package com.hiit.api.web.controller.v1.with;

import com.hiit.api.domain.dao.support.PageableInfo;
import com.hiit.api.domain.dto.request.with.GetWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.usecase.with.GetWithsUseCase;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import com.hiit.api.web.dto.request.PageableInfoRequest;
import com.hiit.api.web.dto.validator.DataId;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import com.hiit.api.web.support.MessageCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
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
			@RequestParam(defaultValue = "false") Boolean my) {
		WithPage withPage = null;
		try {
			Long memberId = Long.valueOf(userDetails.getUsername());
			PageableInfo pageableInfo = new PageableInfoRequest(pageable);
			GetWithsUseCaseRequest request =
					GetWithsUseCaseRequest.builder()
							.memberId(memberId)
							.inItId(id)
							.isMember(my)
							.pageable(pageableInfo)
							.build();
			withPage = getWithsUseCase.execute(request);
			if (withPage == null) {
				withPage = getWithPageMockResponse(pageable);
				return ApiResponseGenerator.success(withPage, HttpStatus.OK, MessageCode.SUCCESS);
			}
			return ApiResponseGenerator.success(withPage, HttpStatus.OK, MessageCode.SUCCESS);
		} catch (Exception e) {
			withPage = getWithPageMockResponse(pageable);
			return ApiResponseGenerator.success(withPage, HttpStatus.OK, MessageCode.SUCCESS);
		}
	}

	private WithPage getWithPageMockResponse(Pageable pageable) {
		WithMemberInfo withMemberInfo =
				WithMemberInfo.builder().name("멤버 이름").profile("멤버 프로필").resolution("멤버 다짐").build();
		WithInfo withInfo1 =
				WithInfo.builder().id(1L).content("윗 내용").hit(10L).withMemberInfo(withMemberInfo).build();
		WithInfo withInfo2 =
				WithInfo.builder().id(2L).content("윗 내용").hit(10L).withMemberInfo(withMemberInfo).build();
		List<WithInfo> withInfos = List.of(withInfo1, withInfo2);
		PageImpl<WithInfo> withInfoPage = new PageImpl<>(withInfos, pageable, withInfos.size());
		return new WithPage(withInfoPage);
	}
}
