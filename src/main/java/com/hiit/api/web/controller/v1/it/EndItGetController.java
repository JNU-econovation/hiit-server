package com.hiit.api.web.controller.v1.it;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.it.EndItResponse;
import com.hiit.api.domain.dto.response.it.list.EndItResponses;
import com.hiit.api.domain.dto.response.together.MyTogetherResponse;
import com.hiit.api.domain.dto.response.together.list.MyTogetherResponses;
import com.hiit.api.web.support.ApiResponse;
import com.hiit.api.web.support.ApiResponseGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/end/its")
@RequiredArgsConstructor
public class EndItGetController {

	@GetMapping()
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> browseIt() {
		EndItResponse it =
				EndItResponse.builder()
						.id(1L)
						.topic("topic")
						.day(1L)
						.period("period")
						.startTime(1L)
						.endTime(2L)
						.participateCount(10L)
						.build();
		ServiceResponse res = new EndItResponses(List.of(it));
		return ApiResponseGenerator.success(res, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ApiResponse<ApiResponse.SuccessBody<ServiceResponse>> readIt(@PathVariable Long id) {
		ServiceResponse res =
				EndItResponse.builder()
						.id(1L)
						.topic("topic")
						.day(1L)
						.period("period")
						.startTime(1L)
						.endTime(2L)
						.participateCount(10L)
						.build();
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
}
