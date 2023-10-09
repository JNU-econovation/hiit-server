package com.hiit.api.web.controller.v1.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.it.DayItResponse;
import com.hiit.api.domain.dto.response.it.ItResponse;
import com.hiit.api.domain.dto.response.it.MemberInItResponse;
import com.hiit.api.domain.dto.response.it.list.DayItResponses;
import com.hiit.api.domain.dto.response.it.list.MemberInItResponses;
import com.hiit.api.domain.dto.response.together.MyTogetherResponse;
import com.hiit.api.domain.dto.response.together.TogetherResponse;
import com.hiit.api.domain.dto.response.together.list.MyTogetherResponses;
import com.hiit.api.domain.dto.response.together.list.TogetherResponses;
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
@RequestMapping("/api/v1/its")
@RequiredArgsConstructor
public class ItGetController {

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseIt() {
		MemberInItResponse it =
				MemberInItResponse.builder()
						.id(1L)
						.topic("topic")
						.startTime(1L)
						.endTime(2L)
						.participatePerson(10L)
						.memberIn(true)
						.build();
		ServiceResponse res = new MemberInItResponses(List.of(it));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/in")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseInIt() {
		DayItResponse it =
				DayItResponse.builder()
						.id(1L)
						.topic("topic")
						.startTime(1L)
						.endTime(2L)
						.participatePerson(10L)
						.day(1L)
						.build();
		ServiceResponse res = new DayItResponses(List.of(it));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readIt(@PathVariable Long id) {
		ServiceResponse res =
				ItResponse.builder()
						.id(1L)
						.topic("topic")
						.startTime(1L)
						.endTime(2L)
						.participatePerson(10L)
						.build();
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{id}/together")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseTogether(
			@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") Boolean all) {
		TogetherResponse together =
				TogetherResponse.builder()
						.id(1L)
						.content("content")
						.memberName("memberName")
						.memberPicture("url")
						.memberComment("comment")
						.hits(10L)
						.build();
		ServiceResponse res = new TogetherResponses(List.of(together));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{id}/together/my")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseMyTogether(
			@PathVariable Long id) {
		MyTogetherResponse together =
				MyTogetherResponse.builder().id(1L).content("content").date(1L).hits(10L).build();
		ServiceResponse res = new MyTogetherResponses(List.of(together));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{id}/together/rank")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseRankTogether(
			@PathVariable Long id) {
		MyTogetherResponse together =
				MyTogetherResponse.builder().id(1L).content("content").date(1L).hits(10L).build();
		ServiceResponse res = new MyTogetherResponses(List.of(together));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}
}
