package com.kasia.validator;

import javax.validation.ConstraintValidatorContext;

public interface ConstraintValidation<T> {

    default void addConstraintViolation(String fieldName, String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.buildConstraintViolationWithTemplate(errorMsg)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }

    default void trimStringFields(T model) {

    }
}
