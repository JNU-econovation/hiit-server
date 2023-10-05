package com.hiit.api.domain.usecase;

import com.hiit.api.common.marker.dto.AbstractDto;
import com.hiit.api.common.marker.dto.request.AbstractRequestDto;

/**
 * UseCase Service 추상화<br>
 * 유즈케이스 서비스에 대한 요청은 구체적인 RequestDto 객체를 통해 진행한다. <br>
 * 유즈케이스 서비스 응답은 AbstractDto를 반환한다.
 *
 * @param <T> Request Dto
 * @param <R> Dto
 */
public interface AbstractUseCaseService<T extends AbstractRequestDto, R extends AbstractDto> {}
