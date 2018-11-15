package com.kasia.service.validation.imp;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;
import com.kasia.service.validation.OperationValidationService;
import com.kasia.service.validation.field.OField;
import com.kasia.service.validation.message.OMessageLink;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationValidationServiceImp implements OperationValidationService {
    @Override
    public boolean isValid(Operation operation, ConstraintValidatorContext constraintValidatorContext) {
        if (operation == null) return true;
        int errors = 0;
        if (operation.getId() < 0) {
            addConstraintViolation(OField.ID, OMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (!isAmountValid(operation.getAmount(), constraintValidatorContext)) {
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

    private boolean isAmountValid(BigDecimal amount, ConstraintValidatorContext constraintValidatorContext) {
        if (amount == null) {
            addConstraintViolation(OField.AMOUNT, OMessageLink.AMOUNT_NULL, constraintValidatorContext);
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            addConstraintViolation(OField.AMOUNT, OMessageLink.AMOUNT_NEGATIVE, constraintValidatorContext);
            return false;
        }
        return true;
    }

    @Override
    public Operation createModelWithFieldAndValue(OField field, Object value) throws FieldNotExistRuntimeException {
        if (value == null || field == null) throw new NullPointerException("Field or value is null");
        Operation operation = new Operation();
        switch (field) {
            case ID:
                operation.setId((Long) value);
                break;
            case CREATE_ON:
                operation.setCreateOn((LocalDateTime) value);
                break;
            case USER:
                operation.setUser((User) value);
                break;
            case AMOUNT:
                operation.setAmount((BigDecimal) value);
                break;
            case ARTICLE:
                operation.setArticle((Article) value);
                break;
            case EMPLOYER:
                operation.setEmployer((Employer) value);
                break;
            default:
                throw new FieldNotExistRuntimeException();
        }
        return operation;
    }
}
