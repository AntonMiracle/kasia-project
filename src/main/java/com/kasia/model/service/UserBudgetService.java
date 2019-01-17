package com.kasia.model.service;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserBudget;

public interface UserBudgetService extends Service<UserBudget> {

    boolean isNameUnique(User user, String budgetName);

    UserBudget findByUserId(long id);

    boolean addBudget(User user, Budget budget);

    boolean removeBudget(User user, Budget budget);

    UserBudget create(User user);
}
