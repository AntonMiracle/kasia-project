package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTONameIsValid;
import com.kasia.model.validation.FieldName;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTONameValidator implements ConstraintValidator<UserDTONameIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    private String fieldName = FieldName.USER_DTO_NAME.getName();
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String fieldValue = vUtil.findStringValue(o, fieldName);

        if (fieldValue == null || fieldValue.length() == 0 || !uValidation.isNameValid(fieldValue)) {
            vUtil.addConstraintViolation(fieldName, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}