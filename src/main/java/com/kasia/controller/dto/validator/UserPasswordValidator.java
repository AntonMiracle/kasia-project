package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserPasswordValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserPasswordValidator implements ConstraintValidator<UserPasswordValid, Object> {
    private String passwordFN;
    private int min;
    private int max;
    private String regex;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserPasswordValid ca) {
        passwordFN = ca.passwordFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, passwordFN);

        if (!isPasswordValid(value)) {
            vUtil.addConstraintViolation(passwordFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String value) {
        if (value == null || value.length() == 0) return false;
        if (value.length() < min) return false;
        if (value.length() > max) return false;
        return value.matches(regex);
    }
}
