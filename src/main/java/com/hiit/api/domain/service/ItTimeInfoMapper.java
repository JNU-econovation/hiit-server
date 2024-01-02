package com.hiit.api.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.domain.model.ItTimeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** IT 시간 정보 변환 서비스 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ItTimeInfoMapper {

	private final ObjectMapper objectMapper;

	public <T extends ItTimeInfo> T read(String info, Class<T> clazz) {
		T timeInfo = null;
		try {
			timeInfo = objectMapper.readValue(info, clazz);
		} catch (JsonProcessingException e) {
			log.error("시간 정보 변환 실패");
		}
		return timeInfo;
	}
}
