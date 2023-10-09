package com.hiit.api.domain.dto.response.support;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Banners implements ServiceResponse {

	private Long size;
	private List<String> urls;
}