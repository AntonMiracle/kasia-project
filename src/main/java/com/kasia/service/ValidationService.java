package com.kasia.service;

import com.kasia.model.Model;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public interface ValidationService<T extends Model> {
    /**
     * @param model
     * @return true if model state is valid otherwise false
     * @throws NullPointerException if argument null
     */
    default boolean isValid(T model) throws NullPointerException{
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }

    default ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
