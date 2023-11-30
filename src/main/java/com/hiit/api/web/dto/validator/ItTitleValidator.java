package com.hiit.api.web.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ItTitleValidator implements ConstraintValidator<ItTitle, String> {

	private static final int MAX_LENGTH = 15;

	@Override
	public void initialize(ItTitle constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.isEmpty()) {
			return false;
		}

		if (value.length() > MAX_LENGTH) {
			return false;
		}

		return true;
	}
}
