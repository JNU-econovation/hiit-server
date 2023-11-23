package com.hiit.api.web.dto.validator;

import com.hiit.api.common.support.DayCodeSpec;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DayCodeValidator implements ConstraintValidator<DayCode, String> {

	@Override
	public void initialize(DayCode constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return false;
		}

		return DayCodeSpec.isExist(value);
	}
}
