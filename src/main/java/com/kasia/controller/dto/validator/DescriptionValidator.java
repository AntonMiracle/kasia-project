package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.DescriptionValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescriptionValidator implements ConstraintValidator<DescriptionValid, Object> {
    private String stringFN;
    private int min;
    private int max;
    private String regex;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(DescriptionValid ca) {
        stringFN = ca.stringFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, stringFN);
        value = value.trim().replaceAll("[ ]{2,}", " ");
        vUtil.setStringValue(o, stringFN, value);
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
