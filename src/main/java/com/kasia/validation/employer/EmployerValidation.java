package com.kasia.validation.employer;

import com.kasia.model.Employer;
import com.kasia.validation.ConstraintViolationManager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployerValidation implements ConstraintValidator<EmployerConstraint, Employer>, ConstraintViolationManager {
    @Override
    public boolean isValid(Employer employer, ConstraintValidatorContext constraintValidatorContext) {
        if (employer == null) return true;
        StringBuilder msg = new StringBuilder();
        if (employer.getId() < 0) {
            msg.append("{validation.message.id}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (employer.getName() == null || employer.getName().trim().length() <= 0) {
            msg.append("{validation.employer.EmployerConstraint.message.name}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
