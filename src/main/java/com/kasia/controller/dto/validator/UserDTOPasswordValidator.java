package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTOPasswordIsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOPasswordValidator implements ConstraintValidator<UserDTOPasswordIsValid, Object> {
    private String passwordFN;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserDTOPasswordIsValid constraintAnnotation) {
        passwordFN = constraintAnnotation.passwordFN();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.getStringValue(o, passwordFN);

        if (passwordV == null || passwordV.length() == 0 ) {
            vUtil.addConstraintViolation(passwordFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}
