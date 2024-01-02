package com.hiit.api.domain.dto.response;

import com.hiit.api.common.marker.dto.AbstractResponse;
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
public class Banners implements AbstractResponse {

	private Long size;
	private List<String> urls;
}
