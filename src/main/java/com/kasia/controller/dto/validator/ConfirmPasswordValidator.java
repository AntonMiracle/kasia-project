package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.ConfirmPasswordValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordValid, Object> {
    private ValidatorUtil vUtil = new ValidatorUtil();
    private String passwordFN;
    private String confirmFN;

    @Override
    public void initialize(ConfirmPasswordValid ca) {
        passwordFN = ca.passwordFN();
        confirmFN = ca.confirmFN();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.getStringValue(object, passwordFN);
        String confirmV = vUtil.getStringValue(object, confirmFN);
        if (passwordV == null || passwordV.length() == 0) {
            return true;
        } else if (confirmV == null || !passwordV.equals(confirmV)) {
            vUtil.addConstraintViolation(confirmFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}