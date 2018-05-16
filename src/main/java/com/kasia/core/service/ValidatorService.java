package com.kasia.core.service;

import com.kasia.core.model.CoreModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public interface ValidatorService<T extends CoreModel> {
    /**
     * Get validator
     *
     * @return validator
     */
    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    default boolean isValid(T model) {
        return getValidator().validate(model).size() == 0;
    }


    /**
     * Mapping validation errors where key is field name and value error msg
     *
     * @param errors Set of errors
     * @return mapped errors, where key is field name and value error msg
     */
    default Map<String, String> mappingFieldMsg(Set<ConstraintViolation<T>> errors) {
        Map<String, String> result = new HashMap<>();

        if (errors == null) return result;

        for (ConstraintViolation<T> error : errors) {
            result.put(error.getPropertyPath().toString(), error.getMessage());
        }
        return result;
    }

    /**
     * If and only if {@link T} is null then return new {@link T} where {@link T#isNull()} return true
     * Otherwise set model fields, where acceptable to switch null on default value
     *
     * @param model
     */
    void setAcceptDefaultValue(T model);

    /**
     * If and only if {@link T} is valid then {@link T#isValid()} return true
     * If and only if {@link T} is null then {@link T#isNull()} return true
     *
     * @param model to validate
     * @return model after validation
     */
    T validation(T model);
}
