package com.kasia.controller.dto.validator;

import com.kasia.model.validation.FieldName;
import com.kasia.model.validation.UserValidation;
import com.kasia.controller.dto.validator.constraint.UserDTOEmailIsValid;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOEmailValidator implements ConstraintValidator<UserDTOEmailIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    private String fieldName = FieldName.USER_DTO_EMAIL.getName();
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String fieldValue = vUtil.findStringValue(o, fieldName);

        if (fieldValue == null || fieldValue.length() == 0  || !uValidation.isEmailValid(fieldValue)) {
            vUtil.addConstraintViolation(fieldName, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}