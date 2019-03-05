package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTOEmailIsValid;
import com.kasia.model.validation.FieldName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOEmailValidator implements ConstraintValidator<UserDTOEmailIsValid, Object> {
    private String fieldName = FieldName.USER_DTO_EMAIL.getName();
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String fieldValue = vUtil.getStringValue(o, fieldName);

        if (fieldValue == null || fieldValue.length() == 0 ) {
            vUtil.addConstraintViolation(fieldName, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}
