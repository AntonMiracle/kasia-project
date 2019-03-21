package com.kasia.controller.dto.validator.constraint;

import com.kasia.controller.dto.validator.PriceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceValidator.class)
public @interface PriceValid {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String priceFN();

    boolean nullable() default false;

    boolean makeTrim() default true;

    int minL() default 0;

    int maxL() default 16;

    String regex();
}
