package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.PlaceNameValid;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlaceNameValidator implements ConstraintValidator<PlaceNameValid, Object> {
    private String nameFN;
    private int min;
    private int max;
    private String regex;
    @Autowired
    private BudgetService budgetS;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(PlaceNameValid ca) {
        nameFN = ca.nameFN();
        min = ca.min();
        max = ca.max();
        regex = ca.regex();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, nameFN);
        value = value.trim().replaceAll("[ ]{2,}", " ");
        vUtil.setStringValue(o, nameFN, value);

        if (!isNameValid(value) || !budgetS.isPlaceNameUnique(vUtil.getLongValue(o, "budgetId"), value)) {
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
