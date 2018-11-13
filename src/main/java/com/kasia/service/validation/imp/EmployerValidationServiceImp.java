package com.kasia.service.validation.imp;

import com.kasia.model.Employer;
import com.kasia.service.validation.EmployerValidationService;

import javax.validation.ConstraintValidatorContext;

public class EmployerValidationServiceImp implements EmployerValidationService {
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
