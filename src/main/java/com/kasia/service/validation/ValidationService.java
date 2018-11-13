package com.kasia.service.validation;

import com.kasia.model.Model;
import com.kasia.service.Service;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public interface ValidationService<T extends Model> extends Service {

    default boolean isValid(T model) throws NullPointerException {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }

    default ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    default void addConstraintViolation(String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(errorMsg)
                .addConstraintViolation();
    }
}
