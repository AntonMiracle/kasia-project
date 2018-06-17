package com.kasia.service.imp;

import com.kasia.model.Model;
import com.kasia.service.ValidationService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidationServiceImp<T extends Model> implements ValidationService<T> {
    private Validator validator;

    public ValidationServiceImp(Validator validator) {
        this.validator = validator;
    }

    public ValidationServiceImp() {
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public Map<String, String> mapErrorFieldsWithMsg(T model) {
        Set<ConstraintViolation<T>> errors = getValidator().validate(model);
        Map<String, String> mapa = new HashMap<>();
        errors.forEach(el -> mapa.put(el.getPropertyPath().toString(), el.getMessage()));
        return mapa;
    }
}
