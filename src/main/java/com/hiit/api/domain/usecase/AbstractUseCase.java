package com.hiit.api.domain.usecase;

import com.hiit.api.common.marker.dto.AbstractRequest;
import com.hiit.api.common.marker.dto.AbstractResponse;

/** 유스케이스의 추상 인터페이스 */
public interface AbstractUseCase<T extends AbstractRequest> {

	/** 유스케이스를 실행 */
	AbstractResponse execute(T request);
}
