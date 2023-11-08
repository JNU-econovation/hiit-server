package com.hiit.api.domain.dao;

import com.hiit.api.common.marker.dto.AbstractData;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/** 도메인 계층에서 사용하는 데이터임의 공통 속성을 정의한 추상클래스 */
@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseData implements AbstractData {

	private final Long id;
	private final LocalDateTime createAt;
	private final LocalDateTime updateAt;
	private final Boolean deleted;
}
