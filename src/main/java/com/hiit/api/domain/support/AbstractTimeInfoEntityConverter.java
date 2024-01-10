package com.hiit.api.domain.support;

import com.hiit.api.common.marker.entity.EntityMarker;
import com.hiit.api.common.marker.model.AbstractDomain;
import com.hiit.api.domain.model.ItTimeDetails;

/** 시간 정보가 포함된 엔티티와 도메인을 변환하는 인터페이스 */
public interface AbstractTimeInfoEntityConverter<T extends EntityMarker, R extends AbstractDomain> {

	/** 엔티티를 도메인으로 변환 */
	R from(T t, ItTimeDetails timeInfo);
}
