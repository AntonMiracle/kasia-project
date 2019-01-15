package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserBudget;
import com.kasia.model.service.UserBudgetService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class UserBudgetServiceImp implements UserBudgetService, ValidationService<UserBudget> {
    @Override
    public UserBudget save(UserBudget model) {
        return null;
    }

    @Override
    public boolean delete(UserBudget model) {
        return false;
    }

    @Override
    public UserBudget getById(long id) {
        return null;
    }

    @Override
    public UserBudget getByUserId(long id) {
        return null;
    }

    @Override
    public UserBudget create(User user) {
        return null;
    }

    @Override
    public boolean addBudget(User user, Budget budget) {
        return false;
    }

    @Override
    public boolean removeBudget(User user, Budget budget) {
        return false;
    }
}
