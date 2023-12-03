package com.hiit.api.repository.entity.business.it;

import javax.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DayCodeListConverter implements AttributeConverter<DayCodeList, String> {

	@Override
	public String convertToDatabaseColumn(DayCodeList attribute) {
		if (attribute == null) {
			return null;
		}

		return attribute.getCode();
	}

	@Override
	public DayCodeList convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		try {
			return DayCodeList.of(dbData);
		} catch (IllegalArgumentException e) {
			log.error("failure to convert cause unexpected day code: {}", dbData);
			throw e;
		}
	}
}
