package com.kasia.service.validation;

import com.kasia.model.Operation;
import com.kasia.service.validation.constraint.OperationConstraint;

import javax.validation.ConstraintValidator;

public interface OperationValidationService extends ConstraintValidator<OperationConstraint, Operation>, ValidationService<Operation> {
}
