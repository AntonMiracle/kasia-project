package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.BalanceValueIsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BalanceValueValidator implements ConstraintValidator<BalanceValueIsValid, Object> {
    private String banknotesFN;
    private ValidatorUtil vUtil = new ValidatorUtil();
    private String pennyFN;

    @Override
    public void initialize(BalanceValueIsValid constraintAnnotation) {
        banknotesFN = constraintAnnotation.banknotesFN();
        pennyFN = constraintAnnotation.pennyFN();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String banknotes = vUtil.findStringValue(o, banknotesFN);
        String penny = vUtil.findStringValue(o, pennyFN);

        if (!vUtil.isBanknotesValueValid(banknotes) || !vUtil.isPennyValueValid(penny)) {
            vUtil.addConstraintViolation(banknotesFN, constraintValidatorContext.getDefaultConstraintMessageTemplate(), constraintValidatorContext);
            return false;
        }
        return true;
    }


}
