package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.StringValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValidator implements ConstraintValidator<StringValid, Object> {
    private boolean nullable;
    private String stringFN;
    private int min;
    private int max;
    private String regex;
    private boolean makeTrim;

    private ValidatorUtil vUtil = new ValidatorUtil();
    private final String REGEX = "^\\S+[[ ]?\\S+]+$";
    private final int MAX = 32;

    @Override
    public void initialize(StringValid ca) {
        stringFN = ca.stringFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
        nullable = ca.nullable();
        makeTrim = ca.makeTrim();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, stringFN);

        if (nullable && value == null) return true;
        if (!nullable && value == null) {
            value = "";
            vUtil.setStringValue(o, stringFN, value);
        }
        if (makeTrim){
            value = value.trim().replaceAll("[ ]{2,}", " ");
            vUtil.setStringValue(o, stringFN, value);
        }
        if (!isStringValid(value)) {
            vUtil.addConstraintViolation(stringFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }

    private boolean isStringValid(String value) {
        if (value.length() < min) return false;
        if (value.length() > max) return false;
        if (value.length() > 0 && !value.matches(regex)) return false;
        return true;
    }
}
