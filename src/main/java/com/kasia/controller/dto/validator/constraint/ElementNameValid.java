package com.kasia.controller.dto.validator.constraint;

import com.kasia.controller.dto.validator.ElementNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ElementNameValidator.class)
public @interface ElementNameValid {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "validation error message not exist";

    String nameFN();

    String typeFN();

    int min() default 0;

    int max() default 250;

    String regex() default "";

    boolean nullable() default false;

    boolean makeTrim() default true;
}
