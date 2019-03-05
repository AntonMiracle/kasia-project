package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.ConfirmPasswordValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordValid, Object> {
    private int min;
    private int max;
    private String regex;
    private ValidatorUtil vUtil = new ValidatorUtil();
    private String passwordFN;
    private String confirmFN;

    @Override
    public void initialize(ConfirmPasswordValid ca) {
        passwordFN = ca.passwordFN();
        confirmFN = ca.confirmFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.getStringValue(object, passwordFN);
        String confirmV = vUtil.getStringValue(object, confirmFN);

        if (passwordV == null || passwordV.length() == 0) {
            return true;
        } else if (!isConfirmValid(confirmV) || !passwordV.equals(confirmV)) {
            vUtil.addConstraintViolation(confirmFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }

    private boolean isConfirmValid(String confirmV) {
        if (confirmV == null) return false;
        if (confirmV.length() < min) return false;
        if (confirmV.length() > max) return false;
        return !(confirmV.length() > 0 && !confirmV.matches(regex));
    }
}