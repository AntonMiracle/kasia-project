package com.kasia.validation.economy;

import com.kasia.model.Economy;
import com.kasia.validation.ValidationHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EconomyValidation implements ConstraintValidator<EconomyConstraint, Economy> {
    private ValidationHelper helper = new ValidationHelper();

    @Override
    public boolean isValid(Economy economy, ConstraintValidatorContext constraintValidatorContext) {
        if (economy == null) return true;
        StringBuilder msg = new StringBuilder();
        if (economy.getName() == null) {
            msg.append("{validation.budget.EconomyConstraint.message.type.null}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
