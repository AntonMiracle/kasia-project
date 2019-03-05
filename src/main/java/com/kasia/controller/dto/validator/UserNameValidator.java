package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserNameValid;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserNameValid, Object> {
    private boolean nullable;
    private String nameFN;
    private int min;
    private int max;
    private String regex;
    private boolean makeTrim;
    @Autowired
    private UserService uService;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserNameValid ca) {
        nameFN = ca.nameFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
        nullable = ca.nullable();
        makeTrim = ca.makeTrim();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, nameFN);
        if (nullable && value == null) return true;
        if (!nullable && value == null) value = "";
        if (makeTrim) value = value.trim().replaceAll("[ ]{2,}", " ");
        vUtil.setStringValue(o, nameFN, value);

        if (!isNameValid(value) || !uService.isUserNameUnique(value)) {
            vUtil.addConstraintViolation(nameFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }

    private boolean isNameValid(String value) {
        if (value.length() < min) return false;
        if (value.length() > max) return false;
        if (value.equals("anonymousUser")) return false;
        return !(value.length() > 0 && !value.matches(regex));
    }
}
