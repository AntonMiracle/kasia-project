package com.kasia.service;

import com.kasia.model.Model;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public interface ValidationService<T extends Model> {
    boolean isValid(T model);

    default ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
