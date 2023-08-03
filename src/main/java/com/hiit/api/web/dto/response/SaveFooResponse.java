package com.hiit.api.web.dto.response;

import com.hiit.api.common.marker.dto.response.AbstractResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SaveFooResponse implements AbstractResponseDto {

	private String name;
}
