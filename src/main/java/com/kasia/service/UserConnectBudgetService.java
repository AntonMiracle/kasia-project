package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserConnectBudget;

public interface UserConnectBudgetService extends Service<UserConnectBudget> {
    UserConnectBudget create(User user);

    boolean addConnectBudget(User user, Budget budget);

    boolean removeConnectBudget(User user, Budget budget);

    UserConnectBudget getByUserId(long id);
}
