package com.kasia.validator;

import com.kasia.model.Model;

import javax.validation.ConstraintValidatorContext;

public interface ConstraintValidation<T extends Model> {

    default void addConstraintViolation(String fieldName, String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.buildConstraintViolationWithTemplate(errorMsg)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }

    void trimStringFields(T model);
}
