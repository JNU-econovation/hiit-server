package com.hiit.api.domain.dao;

import com.hiit.api.common.marker.dto.AbstractData;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseData implements AbstractData {

	private final Long id;
	private final LocalDateTime createAt;
	private final LocalDateTime updateAt;
	private final Boolean deleted;
}
