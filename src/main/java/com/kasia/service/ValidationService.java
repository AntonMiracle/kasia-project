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
    boolean isValid(T model) throws NullPointerException;

    default ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
