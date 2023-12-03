package com.hiit.api.repository.entity.business.hit;

import javax.persistence.AttributeConverter;

public class HitterConverter implements AttributeConverter<Hitter, String> {

	private static final String EMPTY_VALUE = "ANONYMOUS";

	@Override
	public String convertToDatabaseColumn(Hitter attribute) {
		if (attribute == null) {
			return null;
		}
		if (attribute.getId().isEmpty()) {
			return EMPTY_VALUE;
		}
		return attribute.getId().get().toString();
	}

	@Override
	public Hitter convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		if (dbData.equals(EMPTY_VALUE)) {
			return Hitter.anonymous();
		}
		return Hitter.builder().id(Long.parseLong(dbData)).build();
	}
}
