package com.kasia.core.service;

import com.kasia.core.model.Model;
import com.kasia.core.model.Result;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public interface ValidatorService<T extends Model> {

    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }


    default Result<Map<String, String>> mapFieldMsg(Set<ConstraintViolation<T>> errors) throws NullPointerException {
        if (errors == null) throw new NullPointerException();

        Map<String, String> result = new HashMap<>();

        for (ConstraintViolation<T> error : errors) {
            result.put(error.getPropertyPath().toString(), error.getMessage());
        }
        return new Result<>(result);
    }

    Result<T> eliminateNull(T model) throws NullPointerException;

    Result<Boolean> validation(T model) throws NullPointerException;
}
