package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.User;

public interface BudgetService extends CRUDService<Budget> {
    boolean isNameUnique(User user, String name);

    Budget create(String name);

}
