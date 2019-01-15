package com.kasia.model.service;

import com.kasia.model.Budget;
import com.kasia.model.User;

public interface BudgetService extends Service<Budget> {
    boolean isNameUnique(User user, String name);

    Budget create(String name);

}
