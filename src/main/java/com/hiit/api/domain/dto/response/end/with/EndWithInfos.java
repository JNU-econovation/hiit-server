package com.hiit.api.domain.dto.response.end.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class EndWithInfos implements AbstractResponse {

	private final List<EndWithInfo> endWithInfos;
}
