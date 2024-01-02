package com.hiit.api.domain.dto.request.it;

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
public class GetItsUseCaseRequest implements AbstractRequest {

	private Long memberId;
}
