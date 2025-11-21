package com.example.demo.command.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD, METHOD, PARAMETER, RECORD_COMPONENT })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Size(min = 8, max = 13)
@Pattern(regexp = "\\d+")
@ReportAsSingleViolation
public @interface Ean {

    String message() default "invalid EAN (between 8 and 13 number required)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
