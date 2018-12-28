package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.User;

public interface BudgetService extends ValidationService<Budget> {
    boolean isNameUnique(User user, String name);

    Budget save(Budget budget);

    boolean delete(Budget budget);

    Budget getById(long id);

}
