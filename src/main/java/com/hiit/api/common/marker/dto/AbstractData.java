package com.hiit.api.common.marker.dto;

import com.hiit.api.common.marker.entity.EntityMarker;

/**
 * 도메인 계층에서 사용하는 데이터임을 구분하기 위한 마커인터페이스<br>
 * 레포지토리 계층에서 사용하는 엔티티와 대응된다.
 */
public interface AbstractData extends EntityMarker, AbstractDto {}
