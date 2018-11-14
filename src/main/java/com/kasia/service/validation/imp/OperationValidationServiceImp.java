package com.kasia.service.validation.imp;

import com.kasia.model.Operation;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.constraint.OperationConstraint;
import com.kasia.service.validation.field.OField;
import com.kasia.service.validation.message.OMessageLink;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class OperationValidationServiceImp implements ValidationService<Operation, OField, OMessageLink>, ConstraintValidator<OperationConstraint, Operation> {
    @Override
    public boolean isValid(Operation operation, ConstraintValidatorContext constraintValidatorContext) {
        if (operation == null) return true;
        int errors = 0;
        if (operation.getId() < 0) {
            addConstraintViolation(OField.ID, OMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (operation.getAmount() == null || operation.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            addConstraintViolation(OField.AMOUNT, OMessageLink.AMOUNT_NULL, constraintValidatorContext);
            errors++;
        }
        if (operation.getArticle() == null) {
            addConstraintViolation(OField.ARTICLE, OMessageLink.ARTICLE_NULL, constraintValidatorContext);
            errors++;
        }
        if (operation.getEmployer() == null) {
            addConstraintViolation(OField.EMPLOYER, OMessageLink.EMPLOYER_NULL, constraintValidatorContext);
            errors++;
        }
        if (operation.getUser() == null) {
            addConstraintViolation(OField.USER, OMessageLink.USER_NULL, constraintValidatorContext);
            errors++;
        }
        if (operation.getCreateOn() == null) {
            addConstraintViolation(OField.CREATE_ON, OMessageLink.CREATE_ON_NULL, constraintValidatorContext);
            errors++;
        }
        return errors == 0;
    }

    @Override
    public boolean isValueValid(OField field, Object value) {
        return false;
    }

    @Override
    public String getErrorMsgByValue(OField field, Object value) {
        return null;
    }
}
