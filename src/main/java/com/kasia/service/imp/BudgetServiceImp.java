package com.kasia.service.imp;

import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;
import com.kasia.service.BudgetService;

import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.HashSet;

public class BudgetServiceImp implements BudgetService {
    private BudgetRepository repository;

    @Override
    public Budget create(Budget budget) {
        if (budget == null || !(isValid(budget))) return null;
        budget.setArticles(new HashSet<>());
        budget.setStartOn(LocalDateTime.now());
        return repository.save(budget);
    }

    @Override
    public boolean delete(Budget budget) {
        if (budget == null || !(isValid(budget)) || budget.getId() <= 0) return false;
        return repository.delete(budget);
    }

    @Override
    public boolean update(Budget budget) {
        if (budget == null || !(isValid(budget)) || budget.getId() <= 0) return false;
        return repository.update(budget);
    }

    @Override
    public Budget getBudgetById(long id) {
        if (id <= 0) return null;
        return repository.getById(id);
    }

    @Override
    public boolean isValid(Budget model) {
        if (model == null) return false;
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
