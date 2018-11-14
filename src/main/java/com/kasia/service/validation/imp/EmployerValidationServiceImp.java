package com.kasia.service.validation.imp;

import com.kasia.model.Employer;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.constraint.EmployerConstraint;
import com.kasia.service.validation.field.EField;
import com.kasia.service.validation.message.EMessageLink;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployerValidationServiceImp implements  ValidationService<Employer, EField, EMessageLink>, ConstraintValidator<EmployerConstraint, Employer> {
    @Override
    public boolean isValid(Employer employer, ConstraintValidatorContext constraintValidatorContext) {
        if (employer == null) return true;
        int errors = 0;
        if (employer.getId() < 0) {
            addConstraintViolation(EField.ID, EMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (employer.getName() == null || employer.getName().length() <= 0) {
            addConstraintViolation(EField.NAME, EMessageLink.NAME_NULL, constraintValidatorContext);
            errors++;
        }
        return errors == 0;
    }

    @Override
    public boolean isValueValid(EField field, Object value) {
        return false;
    }

    @Override
    public String getErrorMsgByValue(EField field, Object value) {
        return null;
    }
}
