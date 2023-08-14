package com.hiit.api.web.dto.request;

import com.hiit.api.common.marker.dto.request.AbstractRequestDto;
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
public class SaveFooRequest implements AbstractRequestDto {

	private String name;
}
