package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTOConfirmPassIsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOConfirmPassValidator implements ConstraintValidator<UserDTOConfirmPassIsValid, Object> {
    private String passwordFN;
    private String confirmFN;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserDTOConfirmPassIsValid constraintAnnotation) {
        passwordFN = constraintAnnotation.passwordFN();
        confirmFN = constraintAnnotation.confirmFN();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.getStringValue(object, passwordFN);
        String confirmV = vUtil.getStringValue(object, confirmFN);

        if(passwordV == null || passwordV.length() == 0) {
            return true;
        } else if (confirmV == null || confirmV.length() == 0 || !passwordV.equals(confirmV)) {
            vUtil.addConstraintViolation(confirmFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }

}