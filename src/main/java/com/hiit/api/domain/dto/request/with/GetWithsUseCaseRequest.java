package com.hiit.api.domain.dto.request.with;

import com.hiit.api.common.marker.dto.request.AbstractRequestDto;
import com.hiit.api.domain.dao.support.PageableInfo;
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
public class GetWithsUseCaseRequest implements AbstractRequestDto {

	private Long memberId;
	private Long inItId;
	private Boolean isMember;
	private PageableInfo pageable;
}
