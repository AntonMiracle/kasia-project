package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.UserEmailValid;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailValidator implements ConstraintValidator<UserEmailValid, Object> {
    private boolean nullable;
    private String emailFN;
    private int min;
    private int max;
    private String regex;
    private boolean makeTrim;
    @Autowired
    private UserService uService;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(UserEmailValid ca) {
        emailFN = ca.emailFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
        nullable = ca.nullable();
        makeTrim = ca.makeTrim();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, emailFN);
        if (nullable && value == null) return true;
        if (!nullable && value == null) value = "";
        if (makeTrim) value = value.trim().replaceAll("[ ]+", "");
        vUtil.setStringValue(o, emailFN, value);

        if (!isEmailValid(value) || !uService.isUserEmailUnique(value)) {
            vUtil.addConstraintViolation(emailFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String value) {
        if (value.length() < min) return false;
        if (value.length() > max) return false;
        return !(value.length() > 0 && !value.matches(regex));
    }
}
