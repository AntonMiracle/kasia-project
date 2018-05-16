package com.kasia.core.service;

import com.kasia.core.model.CoreModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public interface ValidatorService<T extends CoreModel> {
    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    default boolean isValid(T model) {
        return getValidator().validate(model).size() == 0;
    }

    default Map<String, String> mappingFieldMsg(T model) {
        Map<String, String> result = new HashMap<>();
        Set<ConstraintViolation<T>> errors = getValidator().validate(model);

        for (ConstraintViolation<T> error : errors) {
            result.put(error.getPropertyPath().toString(), error.getMessage());
        }
        return result;
    }

    void setDefaultValuesIfNull(T model);

    /**
     * If and only if {@param model} is valid then {@link T#isValid()} return true
     * If {@param model} is null then {@link T#isNull()} return true
     *
     * @param model to validate
     * @return model after validation
     */
    T validation(T model);
}
