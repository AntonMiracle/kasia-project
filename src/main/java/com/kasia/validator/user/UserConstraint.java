package com.kasia.validator.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UserConstraintValidation.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface UserConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int usernameMinLength() default 6;

    int usernameMaxLength() default 32;

    String usernameRegexp() default "^[A-Za-z0-9_]+$";

    int passwordMinLength() default 6;

    int passwordMaxLength() default 32;

    String passwordRegexp() default "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{3,}$";

    int emailMinLength() default 6;

    int emailMaxLength() default 32;

    String emailRegexp() default "^.+[@].+[.].+$";

}
