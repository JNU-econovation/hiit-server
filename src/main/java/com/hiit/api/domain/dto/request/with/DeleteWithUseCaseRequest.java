package com.hiit.api.domain.dto.request.with;

import com.hiit.api.common.marker.dto.AbstractRequest;
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
public class DeleteWithUseCaseRequest implements AbstractRequest {

	private Long memberId;
	private Long withId;
}
