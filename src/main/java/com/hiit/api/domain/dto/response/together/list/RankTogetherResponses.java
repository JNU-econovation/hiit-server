package com.hiit.api.domain.dto.response.together.list;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dto.response.together.RankTogetherResponse;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RankTogetherResponses implements ServiceResponse {

	private final List<RankTogetherResponse> togethers;
}
