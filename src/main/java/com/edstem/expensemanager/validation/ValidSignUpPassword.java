package com.edstem.expensemanager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordSignUpValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSignUpPassword {
    String message() default
            "Password must meet the following criteria: at least 8 characters long, containing both"
                + " uppercase and lowercase letters, and including at least one digit and special"
                + " character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
