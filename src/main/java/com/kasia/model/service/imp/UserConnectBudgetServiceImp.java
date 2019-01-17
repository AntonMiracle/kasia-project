package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserConnectBudget;
import com.kasia.model.service.UserConnectBudgetService;
import com.kasia.model.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserConnectBudgetServiceImp implements UserConnectBudgetService, ValidationService<UserConnectBudget> {
    @Override
    public UserConnectBudget save(UserConnectBudget model) {
        return null;
    }

    @Override
    public boolean delete(UserConnectBudget model) {
        return false;
    }

    @Override
    public UserConnectBudget findById(long id) {
        return null;
    }

    @Override
    public Set<UserConnectBudget> findAll() {
        return null;
    }

    @Override
    public UserConnectBudget create(User user) {
        return null;
    }

    @Override
    public boolean addConnectBudget(User user, Budget budget) {
        return false;
    }

    @Override
    public boolean removeConnectBudget(User user, Budget budget) {
        return false;
    }

    @Override
    public UserConnectBudget getByUserId(long id) {
        return null;
    }
}
