package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserPasswordIsValid;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNewPasswordValidator implements ConstraintValidator<UserPasswordIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    private String passwordFN;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserPasswordIsValid constraintAnnotation) {
        passwordFN = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.findStringValue(o, passwordFN);

        if(passwordV == null || passwordV.length() == 0) {
            return true;
        }else if (!uValidation.isPasswordValid(passwordV)) {
            vUtil.addConstraintViolation(passwordFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}
