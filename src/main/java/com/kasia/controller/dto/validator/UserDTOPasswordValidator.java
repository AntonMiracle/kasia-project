package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTOPasswordIsValid;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOPasswordValidator implements ConstraintValidator<UserDTOPasswordIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    private String passwordFN;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserDTOPasswordIsValid constraintAnnotation) {
        passwordFN = constraintAnnotation.passwordFN();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.findStringValue(o, passwordFN);

        if (passwordV == null || passwordV.length() == 0 || !uValidation.isPasswordValid(passwordV)) {
            vUtil.addConstraintViolation(passwordFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}
