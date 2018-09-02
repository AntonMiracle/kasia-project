package com.kasia.service;

import com.kasia.model.Budget;

public interface BudgetService extends ValidationService<Budget> {
    Budget create(Budget budget);

    boolean delete(Budget budget);

    boolean update(Budget budget);

    Budget getBudgetById(long id);
}
