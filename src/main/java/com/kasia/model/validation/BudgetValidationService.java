package com.kasia.model.validation;

import com.kasia.model.Budget;

public interface BudgetValidationService extends ValidationService<Budget> {
    boolean isNameValid(String name);
}
