package com.kasia.controller.dto.validator.constraint;

        import com.kasia.controller.dto.validator.UserDTOPasswordValidator;

        import javax.validation.Constraint;
        import javax.validation.Payload;
        import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserDTOPasswordValidator.class)
public @interface UserDTOPasswordIsValid {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordFN() default "";
}
