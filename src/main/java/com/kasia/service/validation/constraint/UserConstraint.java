package com.kasia.service.validation.constraint;

import com.kasia.service.validation.imp.UserValidationServiceImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UserValidationServiceImp.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface UserConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
