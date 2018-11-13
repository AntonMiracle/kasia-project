package com.kasia.service.validation.imp;

import com.kasia.model.Operation;
import com.kasia.service.validation.OperationValidationService;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class OperationValidationServiceImp implements OperationValidationService {
    @Override
    public boolean isValid(Operation operation, ConstraintValidatorContext constraintValidatorContext) {
        if (operation == null) return true;
        StringBuilder msg = new StringBuilder();
        if (operation.getId() < 0) {
            msg.append("{validation.message.id}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (operation.getAmount() == null || operation.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            msg.append("{validation.operation.OperationConstraint.message.amount}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (operation.getArticle() == null) {
            msg.append("{validation.operation.OperationConstraint.message.article}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (operation.getEmployer() == null) {
            msg.append("{validation.operation.OperationConstraint.message.employer}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (operation.getUser() == null) {
            msg.append("{validation.operation.OperationConstraint.message.user}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (operation.getCreateOn() == null) {
            msg.append("{validation.operation.OperationConstraint.message.date}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }
}
