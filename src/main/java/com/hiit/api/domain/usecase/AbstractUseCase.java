package com.hiit.api.domain.usecase;

import com.hiit.api.common.marker.dto.request.AbstractRequestDto;
import com.hiit.api.common.marker.dto.response.ServiceResponse;

/**
 * UseCase 추상화<br>
 * 유즈케이스 계층에 요청은 구체적인 RequestDto 객체를 통해 진행한다. <br>
 * 유즈케이스 계층의 응답은 ServiceResponse 객체를 반환한다.
 *
 * @param <T> Request Dto
 */
public interface AbstractUseCase<T extends AbstractRequestDto> {

	ServiceResponse execute(T request);
}
