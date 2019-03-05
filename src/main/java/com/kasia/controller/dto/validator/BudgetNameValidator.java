package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.BudgetNameValid;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BudgetNameValidator implements ConstraintValidator<BudgetNameValid, Object> {
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
    public void initialize(BudgetNameValid ca) {
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
        User user = uService.findUserByEmail(vUtil.getStringValue(o, "userEmail"));
        if (user == null) return false;
        System.out.println(1);
        if (nullable && value == null) return true;
        System.out.println(2);
        if (!nullable && value == null) value = "";
        System.out.println(3);
        if (makeTrim) value = value.trim().replaceAll("[ ]{2,}", " ");
        System.out.println(4);
        vUtil.setStringValue(o, nameFN, value);
        System.out.println(5);
        System.out.println(value);
        String streamVal = value;
        if (!isNameValid(value)
                || uService.findOwnBudgets(user.getId())
                .stream()
                .filter(budget -> budget.getName().equals(streamVal))
                .count() != 0) {
            System.out.println(6);
            vUtil.addConstraintViolation(nameFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }

    private boolean isNameValid(String value) {
        if (value.length() < min) return false;
        if (value.length() > max) return false;
        return !(value.length() > 0 && !value.matches(regex));
    }
}
