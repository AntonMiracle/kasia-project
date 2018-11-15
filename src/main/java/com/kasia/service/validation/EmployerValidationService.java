package com.kasia.service.validation;

import com.kasia.model.Employer;
import com.kasia.service.validation.constraint.EmployerConstraint;
import com.kasia.service.validation.field.EField;
import com.kasia.service.validation.message.EMessageLink;
import com.kasia.service.validation.regex.ERegex;

import javax.validation.ConstraintValidator;

public interface EmployerValidationService extends ValidationService<Employer, EField, EMessageLink, ERegex>, ConstraintValidator<EmployerConstraint, Employer> {
}
