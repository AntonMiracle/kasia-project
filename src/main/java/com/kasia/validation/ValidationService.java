package com.kasia.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ValidationService<T> {

    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    default Map<String, String> mapErrorFieldsWithMsg(Set<ConstraintViolation<T>> errors) {
        Map<String, String> mapa = new HashMap<>();
        errors.forEach(el -> mapa.put(el.getPropertyPath().toString(), el.getMessage()));
        return mapa;
    }

}
