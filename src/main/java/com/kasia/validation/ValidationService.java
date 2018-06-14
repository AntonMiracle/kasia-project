package com.kasia.validation;

import com.kasia.model.user.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

public interface ValidationService<T> {

    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    default Map<String, String> getErrorFieldsAndMsg(Set<ConstraintViolation<T>> errors) {

    }

}
