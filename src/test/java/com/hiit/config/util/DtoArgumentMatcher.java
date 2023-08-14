package com.hiit.config.util;

import com.hiit.api.common.marker.dto.AbstractDto;
import org.mockito.ArgumentMatchers;

public class DtoArgumentMatcher extends ArgumentMatchers {

	public static <T extends AbstractDto> T anyDto(Class<T> type) {
		if (type.getSuperclass().isInstance(AbstractDto.class)) {
			return ArgumentMatchers.any(type);
		}
		throw new IllegalArgumentException("Dto 타입의 클래스만 사용 가능합니다.");
	}
}
