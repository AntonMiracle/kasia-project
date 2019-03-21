package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.BudgetNameValid;
import com.kasia.model.service.BudgetService;
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
    private BudgetService budgetS;
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
        long userId = vUtil.getLongValue(o, "userId");
        if (userId < 1) return false;
        if (nullable && value == null) return true;
        if (!nullable && value == null) value = "";
        if (makeTrim) value = value.trim().replaceAll("[ ]{2,}", " ");
        vUtil.setStringValue(o, nameFN, value);

        if (!isNameValid(value) || !budgetS.isOwnBudgetNameUnique(userId, value)) {
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
