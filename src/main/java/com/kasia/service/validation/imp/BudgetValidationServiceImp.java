package com.kasia.service.validation.imp;

import com.kasia.model.Budget;
import com.kasia.service.validation.BudgetValidationService;

import javax.validation.ConstraintValidatorContext;

public class BudgetValidationServiceImp implements BudgetValidationService {

    @Override
    public boolean isValid(Budget budget, ConstraintValidatorContext constraintValidatorContext) {
        if (budget == null) return true;
        StringBuilder msg = new StringBuilder();
        if (budget.getId() < 0) {
            msg.append("{validation.message.id}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getName() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.type}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getBalance() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.balance}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getCurrency() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.currency}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (budget.getCreateOn() == null) {
            msg.append("{validation.budget.BudgetConstraint.message.date}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
