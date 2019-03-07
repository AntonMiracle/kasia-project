package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.PriceAmountValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceAmountValidator implements ConstraintValidator<PriceAmountValid, Object> {
    private boolean nullable;
    private String balanceFN;
    private int minL;
    private int maxL;
    private String regex = "^[-+]?\\d+|[-+]?\\d+[.,]\\d+|[-+]?\\d+[.,]|[.,]\\d+$";
    private boolean makeTrim;

    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(PriceAmountValid ca) {
        balanceFN = ca.priceFN();
        minL = ca.minL();
        maxL = ca.maxL();
        nullable = ca.nullable();
        makeTrim = ca.makeTrim();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.getStringValue(o, balanceFN);
        if (nullable && value == null) return true;
        if (!nullable && value == null) {
            value = "0";
        }
        if (makeTrim) {
            value = value.trim().replaceAll("[ ]+", "");
        }
        value = value.replaceAll("[,]", ".");
        vUtil.setStringValue(o, balanceFN, value);
        if (!isStrValueValid(value)) {
            vUtil.addConstraintViolation(balanceFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }
        return true;
    }

    private boolean isStrValueValid(String value) {
        if (value.length() < minL) return false;
        if (value.length() > maxL) return false;
        return value.matches(regex);
    }

}
