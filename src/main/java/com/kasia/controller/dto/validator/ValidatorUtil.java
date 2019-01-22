package com.kasia.controller.dto.validator;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class ValidatorUtil {

    public String findStringValue(Object object, String fieldName) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (String) f.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public void addConstraintViolation(String fieldName, String msg, ConstraintValidatorContext cvContext) {
        cvContext.disableDefaultConstraintViolation();
        cvContext.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(fieldName).addConstraintViolation();
    }
}
