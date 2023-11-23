package com.hiit.api.web.dto.validator;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WithValidator implements ConstraintValidator<With, String> {

	private static final int MAX_LENGTH = 20;

	@Override
	public void initialize(With constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return false;
		}

		if (value.isEmpty()) {
			return false;
		}

		if (value.length() > MAX_LENGTH) {
			return false;
		}

		return true;
	}
}
