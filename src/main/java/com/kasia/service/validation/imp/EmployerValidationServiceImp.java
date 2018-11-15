package com.kasia.service.validation.imp;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Employer;
import com.kasia.service.validation.EmployerValidationService;
import com.kasia.service.validation.field.EField;
import com.kasia.service.validation.message.EMessageLink;
import com.kasia.service.validation.regex.ERegex;

import javax.validation.ConstraintValidatorContext;

public class EmployerValidationServiceImp implements EmployerValidationService {
    @Override
    public boolean isValid(Employer employer, ConstraintValidatorContext constraintValidatorContext) {
        if (employer == null) return true;
        int errors = 0;
        if (employer.getId() < 0) {
            addConstraintViolation(EField.ID, EMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (!isNameValid(employer.getName(),constraintValidatorContext)) {
            errors++;
        }
        if(!isDescriptionValid(employer.getDescription(),constraintValidatorContext)){
            errors++;
        }
        return errors == 0;
    }

    private boolean isDescriptionValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        if (description == null) {
            addConstraintViolation(EField.DESCRIPTION, EMessageLink.DESCRIPTION_NULL, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isNameValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            addConstraintViolation(EField.NAME,EMessageLink.NAME_NULL, constraintValidatorContext);
            return false;
        }
        if (!isMatch(name, ERegex.NAME)) {
            addConstraintViolation(EField.NAME, EMessageLink.NAME_REGEX_ERROR, constraintValidatorContext);
            return false;
        }
        return true;
    }

    @Override
    public Employer createModelWithFieldAndValue(EField field, Object value) throws FieldNotExistRuntimeException {
        if (value == null || field == null) throw new NullPointerException("Field or value is null");
        Employer employer = new Employer();
        switch (field) {
            case ID:
                employer.setId((Long) value);
                break;
            case NAME:
                employer.setName((String) value);
                break;
            case DESCRIPTION:
                employer.setDescription((String) value);
                break;
            default:
                throw new FieldNotExistRuntimeException();
        }
        return employer;
    }
}
