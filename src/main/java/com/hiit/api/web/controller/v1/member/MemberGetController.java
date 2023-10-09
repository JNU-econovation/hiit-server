package com.hiit.api.web.controller.v1.member;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.it.CommonItResponse;
import com.hiit.api.domain.dto.response.it.list.CommonItResponses;
import com.hiit.api.domain.dto.response.member.FriendResponse;
import com.hiit.api.domain.dto.response.member.ProfileResponse;
import com.hiit.api.domain.dto.response.member.StatusMemberResponse;
import com.hiit.api.domain.dto.response.member.list.FriendResponses;
import com.hiit.api.domain.dto.response.together.MemberItCommonTogetherResponse;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberGetController {

	@GetMapping("/friends")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseFriend(
			@RequestParam(required = false, defaultValue = "false") Boolean ban) {
		FriendResponse friend =
				FriendResponse.builder().id(1L).name("name").picture("url").itCommonCount(10L).build();
		ServiceResponse res = new FriendResponses(List.of(friend));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/mypage")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> mypage() {
		ServiceResponse res =
				ProfileResponse.builder()
						.id(1L)
						.name("name")
						.comment("comment")
						.picture("url")
						.friendCount(10L)
						.itInCount(10L)
						.build();
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{mid}")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> read(@PathVariable Long mid) {
		StatusMemberResponse res =
				StatusMemberResponse.builder()
						.id(1L)
						.name("name")
						.comment("comment")
						.picture("url")
						.friendStatus(true)
						.banStatus(true)
						.build();
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{mid}/common/its")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseCommonIts(
			@PathVariable Long mid) {
		CommonItResponse itResponse =
				CommonItResponse.builder()
						.id(1L)
						.topic("topic")
						.startTime(1L)
						.endTime(2L)
						.commonDays(1L)
						.notiStatus(true)
						.build();
		ServiceResponse res = new CommonItResponses(List.of(itResponse));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{mid}/common/its/{iid}/together")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readMemberItCommonTogether(
			@PathVariable Long mid, @PathVariable Long iid) {
		MemberItCommonTogetherResponse res =
				MemberItCommonTogetherResponse.builder()
						.participateCount(1L)
						.memberId(1L)
						.memberName("name")
						.memberPicture("url")
						.build();
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}
}
