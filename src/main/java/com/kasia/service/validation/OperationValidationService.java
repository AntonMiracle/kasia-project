package com.kasia.service.validation;

import com.kasia.model.Operation;
import com.kasia.service.validation.constraint.OperationConstraint;
import com.kasia.service.validation.field.OField;
import com.kasia.service.validation.message.OMessageLink;
import com.kasia.service.validation.regex.ORegex;

import javax.validation.ConstraintValidator;

public interface OperationValidationService extends ValidationService<Operation, OField, OMessageLink, ORegex>, ConstraintValidator<OperationConstraint, Operation> {
}
