package com.kasia.validator.unit;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UnitConstraintValidation.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface UnitConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int nameMinSize() default 1;

    int nameMaxSize() default 12;

    String nameRegexp() default "^[A-Za-z]+[0-9]*$";

}
