package com.hiit.api.web.dto.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = TimeDividedBy30MinutesValidator.class)
public @interface TimeDividedBy30Minutes {
	String message() default "Time divided by 30 minutes format validation failed.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
