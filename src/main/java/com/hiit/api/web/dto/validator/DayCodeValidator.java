package com.hiit.api.web.dto.validator;

import com.hiit.api.common.support.DayCodeSpec;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DayCodeValidator implements ConstraintValidator<DayCode, String> {

	@Override
	public void initialize(DayCode constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return DayCodeSpec.isExist(value);
	}
}
