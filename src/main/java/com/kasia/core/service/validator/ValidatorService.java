package com.kasia.core.service.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public interface ValidatorService<T> {

    default Set<ConstraintViolation<T>> validate(T object) {
        return getValidator().validate(object);
    }

    default Validator getValidator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }

    default boolean isValid(T object) {
        return validate(object).size() == 0;
    }

    default long countFields(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.getName().matches("[A-Za-z]+[A-Za-z0-9_$]")).count();
    }

    void trimFields(T object);
}
