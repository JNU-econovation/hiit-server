package com.hiit.api.domain.dto.response.hit;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
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
public class HitInfo implements ServiceResponse {

	private Long count;
}
