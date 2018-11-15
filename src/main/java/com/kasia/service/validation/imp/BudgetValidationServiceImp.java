package com.kasia.service.validation.imp;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.Operation;
import com.kasia.service.validation.BudgetValidationService;
import com.kasia.service.validation.field.BField;
import com.kasia.service.validation.message.BMessageLink;
import com.kasia.service.validation.regex.BRegex;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class BudgetValidationServiceImp implements BudgetValidationService {

    @Override
    public boolean isValid(Budget budget, ConstraintValidatorContext constraintValidatorContext) {
        if (budget == null) return true;
        int errors = 0;
        if (budget.getId() < 0) {
            addConstraintViolation(BField.ID, BMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (!isNameValid(budget.getName(), constraintValidatorContext)) {
            errors++;
        }
        if (!isBalanceValid(budget.getBalance(), constraintValidatorContext)) {
            errors++;
        }
        if (!isCurrencyValid(budget.getCurrency(), constraintValidatorContext)) {
            errors++;
        }
        if (budget.getCreateOn() == null) {
            addConstraintViolation(BField.CREATE_ON, BMessageLink.CREATE_ON_NULL, constraintValidatorContext);
            errors++;
        }
        if (budget.getOperations() == null) {
            addConstraintViolation(BField.OPERATIONS, BMessageLink.OPERATIONS_NULL, constraintValidatorContext);
            errors++;
        }
        return errors == 0;
    }

    private boolean isNameValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            addConstraintViolation(BField.NAME, BMessageLink.NAME_NULL, constraintValidatorContext);
            return false;
        }
        if (!isMatch(name, BRegex.NAME)) {
            addConstraintViolation(BField.NAME, BMessageLink.NAME_REGEX_ERROR, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isBalanceValid(BigDecimal balance, ConstraintValidatorContext constraintValidatorContext) {
        if (balance == null) {
            addConstraintViolation(BField.BALANCE, BMessageLink.BALANCE_NULL, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isCurrencyValid(Currency currency, ConstraintValidatorContext constraintValidatorContext) {
        if (currency == null) {
            addConstraintViolation(BField.CURRENCY, BMessageLink.CURRENCY_NULL, constraintValidatorContext);
            return false;
        }
        if (!getAvailableCurrency().contains(currency)) {
            addConstraintViolation(BField.CURRENCY, BMessageLink.CURRENCY_NOT_AVAILABLE, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private Set<Currency> getAvailableCurrency() {
        Set<Currency> available = new HashSet<>();
        available.add(Currency.getInstance("USD"));
        available.add(Currency.getInstance("EUR"));
        available.add(Currency.getInstance("RUB"));
        available.add(Currency.getInstance("UAH"));
        available.add(Currency.getInstance("PLN"));
        return available;
    }

    @Override
    public Budget createModelWithFieldAndValue(BField field, Object value) throws FieldNotExistRuntimeException {
        if (value == null || field == null) throw new NullPointerException("Field or value is null");
        Budget budget = new Budget();
        switch (field) {
            case ID:
                budget.setId((Long) value);
                break;
            case CREATE_ON:
                budget.setCreateOn((LocalDateTime) value);
                break;
            case NAME:
                budget.setName((String) value);
                break;
            case BALANCE:
                budget.setBalance((BigDecimal) value);
                break;
            case CURRENCY:
                budget.setCurrency((Currency) value);
                break;
            case OPERATIONS:
                budget.setOperations((Set<Operation>) value);
                break;
            default:
                throw new FieldNotExistRuntimeException();
        }
        return budget;
    }
}
