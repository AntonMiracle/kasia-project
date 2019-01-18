package com.kasia.model.validation;

import com.kasia.model.Budget;

import javax.validation.ValidationException;


public interface BudgetValidation extends Validation<Budget> {
    boolean isNameValid(String name);

    default void verifyName(String name) {
        if (!isNameValid(name)) throw new ValidationException();
    }
}
