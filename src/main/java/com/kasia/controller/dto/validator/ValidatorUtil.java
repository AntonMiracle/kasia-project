package com.kasia.controller.dto.validator;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.math.BigInteger;

public class ValidatorUtil {

    public String getStringValue(Object object, String fieldName) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (String) f.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public void setStringValue(Object object, String fieldName, String value) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }

    public long getLongValue(Object object, String fieldName) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (long) f.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return 0;
        }
    }

    public void addConstraintViolation(String fieldName, String msg, ConstraintValidatorContext cvContext) {
        cvContext.disableDefaultConstraintViolation();
        cvContext.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(fieldName).addConstraintViolation();
    }

    private boolean isIntegerValue(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean isPennyValueValid(String penny) {
        if (isIntegerValue(penny)) {
            int result = Integer.parseInt(penny);
            return result < 100 && result >= 0;
        }
        return false;
    }

    public boolean isBanknotesValueValid(String banknotes) {
        return isIntegerValue(banknotes);
    }
}
