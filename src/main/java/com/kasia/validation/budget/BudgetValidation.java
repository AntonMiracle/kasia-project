package com.kasia.validation.budget;

import com.kasia.model.Budget;
import com.kasia.validation.ValidationHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BudgetValidation implements ConstraintValidator<BudgetConstraint, Budget> {
    private ValidationHelper helper = new ValidationHelper();

    @Override
    public boolean isValid(Budget budget, ConstraintValidatorContext constraintValidatorContext) {
        if (budget == null) return true;
        StringBuilder msg = new StringBuilder();
        if (budget.getId() < 0) {
            msg.append("{validation.message.id}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getName() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.type}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getBalance() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.balance}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getCurrency() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.currency}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if(budget.getCreateOn() == null){
            msg.append("{validation.budget.BudgetConstraint.message.date}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
