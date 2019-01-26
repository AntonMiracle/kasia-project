package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserConfirmPasswordIsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserConfirmPasswordValidator implements ConstraintValidator<UserConfirmPasswordIsValid, Object> {
    private String passwordFN;
    private String confirmFN;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserConfirmPasswordIsValid constraintAnnotation) {
        passwordFN = constraintAnnotation.passwordFN();
        confirmFN = constraintAnnotation.confirmFN();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.findStringValue(object, passwordFN);
        String confirmV = vUtil.findStringValue(object, confirmFN);

        if (passwordV == null || confirmV == null || passwordV.length() == 0 || confirmV.length() == 0 || !passwordV.equals(confirmV)) {
            vUtil.addConstraintViolation(confirmFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }

}