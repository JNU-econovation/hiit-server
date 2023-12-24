package com.hiit.api.domain.dto.request.with;

import com.hiit.api.common.marker.dto.request.AbstractRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateWithUseCaseRequest implements AbstractRequestDto {

	private Long memberId;
	private Long inItId;
	private String content;
}
