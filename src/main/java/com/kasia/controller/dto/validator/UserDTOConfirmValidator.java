package com.kasia.controller.dto.validator;

import com.kasia.model.validation.FieldName;
import com.kasia.controller.dto.validator.constraint.UserDTOConfirmIsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDTOConfirmValidator implements ConstraintValidator<UserDTOConfirmIsValid, Object> {
    private String passwordFN = FieldName.USER_DTO_PASSWORD.getName();
    private String confirmFN = FieldName.USER_DTO_CONFIRM.getName();
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext cvContext) {
        String passwordV = vUtil.findStringValue(object, passwordFN);
        String confirmV = vUtil.findStringValue(object, confirmFN);

        if (passwordV == null || confirmV == null || passwordV.length() == 0 || confirmV.length() == 0 || !passwordV.equals(confirmV)) {
            vUtil.addConstraintViolation(confirmFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }

}