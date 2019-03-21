package com.kasia.model.service;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.Budget;

import java.util.Set;

public interface BudgetService {
    boolean setOwner(long budgetId, long userId);

    Set<Budget> findOwnBudgets(long userId);

    Budget findById(long budgetId);

    boolean isOwnBudgetNameUnique(long userId, String name);

    Budget save(Budget budget);

    Budget convert(BudgetAdd budgetAdd);
}
