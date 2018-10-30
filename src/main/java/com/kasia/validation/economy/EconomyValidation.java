package com.kasia.validation.economy;

import com.kasia.model.Economy;
import com.kasia.validation.ConstraintViolationManager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EconomyValidation implements ConstraintValidator<EconomyConstraint, Economy>, ConstraintViolationManager {

    @Override
    public boolean isValid(Economy economy, ConstraintValidatorContext constraintValidatorContext) {
        if (economy == null) return true;
        StringBuilder msg = new StringBuilder();
        if (economy.getId() < 0) {
            msg.append("{validation.message.id}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (economy.getName() == null) {
            msg.append("{validation.budget.EconomyConstraint.message.name}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (economy.getCreateOn() == null) {
            msg.append("{validation.budget.EconomyConstraint.message.date}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
