package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTOPasswordIsValid;
import com.kasia.model.validation.FieldName;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOPasswordValidator implements ConstraintValidator<UserDTOPasswordIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    private String fieldName = FieldName.USER_DTO_PASSWORD.getName();
    private ValidatorUtil vUtil = new ValidatorUtil();

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
