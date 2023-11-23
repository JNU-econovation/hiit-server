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
@Constraint(validatedBy = DayCodeValidator.class)
public @interface DayCode {
	String message() default "DayCode validation failed.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
