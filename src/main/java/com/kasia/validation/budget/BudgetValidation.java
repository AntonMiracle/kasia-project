package com.kasia.validation.budget;

import com.kasia.model.Budget;
import com.kasia.validation.ValidationHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BudgetValidation implements ConstraintValidator<BudgetConstraint, Budget> {
    private ValidationHelper helper = new ValidationHelper();

    @Override
    public void initialize(BudgetConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Budget budget, ConstraintValidatorContext constraintValidatorContext) {
        if (budget == null) return true;
        StringBuilder msg = new StringBuilder();
        if (budget.getName() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.type.null}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getBalance() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.balance.null}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getCurrency() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.currency.null}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
