package com.kasia.service.validation;

import com.kasia.model.Budget;
import com.kasia.service.validation.constraint.BudgetConstraint;
import com.kasia.service.validation.field.BField;
import com.kasia.service.validation.message.BMessageLink;
import com.kasia.service.validation.regex.BRegex;

import javax.validation.ConstraintValidator;

public interface BudgetValidationService extends ValidationService<Budget, BField, BMessageLink, BRegex>, ConstraintValidator<BudgetConstraint, Budget> {
}
