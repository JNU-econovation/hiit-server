package com.hiit.api.web.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdPositiveValidator implements ConstraintValidator<DataId, Long> {

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		return isOverZero(value);
	}

	private boolean isOverZero(Long value) {
		return value >= 0;
	}

	@Override
	public void initialize(DataId constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
