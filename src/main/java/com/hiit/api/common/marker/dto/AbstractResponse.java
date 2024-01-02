package com.hiit.api.common.marker.dto;

/** 계층간 반환하는 응답임을 구분하기 하기 위한 마커인터페이스 */
public interface AbstractResponse extends AbstractDto {
	AbstractResponse VOID = new AbstractResponse() {};
}
