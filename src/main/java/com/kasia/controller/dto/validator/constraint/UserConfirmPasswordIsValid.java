package com.kasia.controller.dto.validator.constraint;

import com.kasia.controller.dto.validator.UserConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserConfirmPasswordValidator.class)
public @interface UserConfirmPasswordIsValid {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordFN() default "";

    String confirmFN() default "";
}