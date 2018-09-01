package com.kasia.validation;

import javax.validation.ConstraintValidatorContext;

public class ValidationHelper {

    public void addConstraintViolation(String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(errorMsg)
                .addConstraintViolation();
    }

}
