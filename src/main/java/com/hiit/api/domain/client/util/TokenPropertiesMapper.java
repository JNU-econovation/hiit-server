package com.hiit.api.domain.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenPropertiesMapper {

	private final ObjectMapper objectMapper;

	public <T> T read(String info, Class<T> clazz) {
		T properties = null;
		try {
			properties = objectMapper.readValue(info, clazz);
		} catch (JsonProcessingException e) {
			log.error("프로퍼티 매핑 실패");
		}
		return properties;
	}
}
