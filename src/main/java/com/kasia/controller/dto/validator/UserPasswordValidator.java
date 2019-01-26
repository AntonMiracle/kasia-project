package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserPasswordIsValid;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserPasswordValidator implements ConstraintValidator<UserPasswordIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    private String fieldName;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserPasswordIsValid constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String fieldValue = vUtil.findStringValue(o, fieldName);

        if (fieldValue == null || fieldValue.length() == 0 || !uValidation.isPasswordValid(fieldValue)) {
            vUtil.addConstraintViolation(fieldName, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}
