package com.kasia.controller.dto.validator.constraint;

import com.kasia.controller.dto.validator.UserNewPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNewPasswordValidator.class)
public @interface UserPasswordIsValid {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName() default "";
}
