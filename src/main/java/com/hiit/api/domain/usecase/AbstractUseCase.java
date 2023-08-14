package com.hiit.api.domain.usecase;

import com.hiit.api.common.marker.dto.request.AbstractRequestDto;
import com.hiit.api.common.marker.dto.response.AbstractResponseDto;

/**
 * UseCase 추상화<br>
 * 계층 사이의 이동에 Dto를 사용을 강제하기 위해 사용한다.
 *
 * @param <T> Request Dto
 * @param <R> Response Dto
 */
public interface AbstractUseCase<T extends AbstractRequestDto, R extends AbstractResponseDto> {

	R execute(T request);
}
