package com.hiit.api.web.dto.validator;

import java.time.LocalTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeDividedBy30MinutesValidator
		implements ConstraintValidator<TimeDividedBy30Minutes, LocalTime> {

	@Override
	public void initialize(TimeDividedBy30Minutes constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
		int minute = value.getMinute();

		if (minute % 30 != 0) {
			return false;
		}

		return true;
	}
}
