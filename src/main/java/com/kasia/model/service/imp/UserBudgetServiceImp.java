package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserBudget;
import com.kasia.model.service.UserBudgetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserBudgetServiceImp implements UserBudgetService {
    @Override
    public UserBudget save(UserBudget model) {
        return null;
    }

    @Override
    public boolean delete(UserBudget model) {
        return false;
    }

    @Override
    public UserBudget findById(long id) {
        return null;
    }

    @Override
    public Set<UserBudget> findAll() {
        return null;
    }

    @Override
    public boolean isNameUnique(User user, String budgetName) {
        return false;
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
