package com.kasia.service.validation.constraint;

import com.kasia.service.validation.imp.BudgetValidationServiceImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {BudgetValidationServiceImp.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface BudgetConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
