package com.kasia.controller.dto.validator.constraint;

import com.kasia.controller.dto.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailValid {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "validation error message not exist";

    String emailFN();

    int min() default 0;

    int max() default 250;

    String regex() default "";

    boolean nullable() default false;

    boolean makeTrim() default true;
}