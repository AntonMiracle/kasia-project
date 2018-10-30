package com.kasia.validation;

import javax.validation.ConstraintValidatorContext;

public interface ConstraintViolationManager {

    default void addConstraintViolation(String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(errorMsg)
                .addConstraintViolation();
    }

}
