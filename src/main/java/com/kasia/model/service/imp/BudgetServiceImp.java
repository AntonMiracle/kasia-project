package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.service.BudgetService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BudgetServiceImp implements BudgetService, ValidationService<Budget> {
    @Override
    public Budget save(Budget model) {
        return null;
    }

    @Override
    public boolean delete(Budget model) {
        return false;
    }

    @Override
    public Budget findById(long id) {
        return null;
    }

    @Override
    public Set<Budget> findAll() {
        return null;
    }

    @Override
    public boolean isNameUnique(User user, String name) {
        return false;
    }

    @Override
    public Budget create(String name) {
        return null;
    }
}