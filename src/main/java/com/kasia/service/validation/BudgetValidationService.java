package com.kasia.service.validation;

import com.kasia.model.Budget;
import com.kasia.service.validation.constraint.BudgetConstraint;

import javax.validation.ConstraintValidator;

public interface BudgetValidationService extends ConstraintValidator<BudgetConstraint, Budget>, ValidationService<Budget> {
}
