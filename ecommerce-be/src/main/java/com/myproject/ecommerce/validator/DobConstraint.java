package com.myproject.ecommerce.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "NO_MESSAGE_IN_VALIDATION"; // notify the mandatory message for validated field data

    int minOld();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
