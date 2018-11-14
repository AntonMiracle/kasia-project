package com.kasia.service.validation.imp;

import com.kasia.model.Budget;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.constraint.BudgetConstraint;
import com.kasia.service.validation.field.BField;
import com.kasia.service.validation.message.BMessageLink;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BudgetValidationServiceImp implements ValidationService<Budget, BField, BMessageLink>, ConstraintValidator<BudgetConstraint, Budget> {

    @Override
    public boolean isValid(Budget budget, ConstraintValidatorContext constraintValidatorContext) {
        if (budget == null) return true;
        int errors = 0;
        if (budget.getId() < 0) {
            addConstraintViolation(BField.ID, BMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (budget.getName() == null || budget.getName().length() == 0) {
            addConstraintViolation(BField.NAME, BMessageLink.NAME_NULL, constraintValidatorContext);
            errors++;
        }
        if (budget.getBalance() == null) {
            addConstraintViolation(BField.BALANCE, BMessageLink.BALANCE_NULL, constraintValidatorContext);
            errors++;
        }
        if (budget.getCurrency() == null) {
            addConstraintViolation(BField.CURRENCY, BMessageLink.CURRENCE_NULL, constraintValidatorContext);
            errors++;
        }
        if (budget.getCreateOn() == null) {
            addConstraintViolation(BField.CREATE_ON, BMessageLink.CREATE_ON_NULL, constraintValidatorContext);
            errors++;
        }
        return errors == 0;
    }

    @Override
    public boolean isValueValid(BField field, Object value) {
        return false;
    }

    @Override
    public String getErrorMsgByValue(BField field, Object value) {
        return null;
    }
}
