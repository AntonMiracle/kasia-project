package com.kasia.service.validation;

import com.kasia.model.Employer;
import com.kasia.service.validation.constraint.EmployerConstraint;

import javax.validation.ConstraintValidator;

public interface EmployerValidationService extends ConstraintValidator<EmployerConstraint, Employer>, ValidationService<Employer> {
}
