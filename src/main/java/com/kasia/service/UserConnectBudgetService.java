package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserConnectBudget;

public interface UserConnectBudgetService extends CRUDService<UserConnectBudget> {
    UserConnectBudget create(User user);

    boolean addBudget(User user, Budget budget);

    boolean removeBudget(User user, Budget budget);

    UserConnectBudget getByUserId(long id);
}
