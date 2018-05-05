package com.kasia.core.service;

import com.kasia.core.model.CoreModel;
import com.kasia.core.model.GroupType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;


public interface ValidatorService<T extends CoreModel> {
    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    default boolean isValid(T model) {
        return getValidator().validate(model).size() == 0;
    }

    default Map<String, String> mappingFieldMsg(GroupType model) {
        Map<String, String> result = new HashMap<>();
        Set<ConstraintViolation<GroupType>> errors = getValidator().validate(model);

        for (ConstraintViolation<GroupType> error : errors) {
            result.put(error.getPropertyPath().toString(), error.getMessage());
        }
        return result;
    }

    void setDefaultValuesIfNull(T model);

}
