package com.hiit.api.repository.document.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Converter
public class MemberStatConverter implements AttributeConverter<MemberStat, String> {

	private final ObjectMapper objectMapper;

	@Override
	public String convertToDatabaseColumn(MemberStat attribute) {
		if (attribute == null) {
			return null;
		}

		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	@Override
	public MemberStat convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		try {
			return objectMapper.readValue(dbData, MemberStat.class);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
