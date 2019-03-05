package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserDTONameIsValid;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.FieldName;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTONameValidator implements ConstraintValidator<UserDTONameIsValid, Object> {
    @Autowired
    private UserValidation uValidation;
    @Autowired
    private UserService uService;
    private String fieldName = FieldName.USER_DTO_NAME.getName();
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String fieldValue = vUtil.getStringValue(o, fieldName);

        if (fieldValue == null || fieldValue.length() == 0
                || !uValidation.isNameValid(fieldValue)
                || fieldValue.equals("anonymousUser")
                || !uService.isUserNameUnique(fieldValue)) {
            vUtil.addConstraintViolation(fieldName, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }
}
