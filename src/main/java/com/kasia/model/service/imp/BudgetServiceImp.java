package com.kasia.model.service.imp;

import com.kasia.model.Balance;
import com.kasia.model.Budget;
import com.kasia.model.repository.BudgetRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.validation.BudgetValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BudgetServiceImp implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private BudgetValidationService budgetValidationService;

    @Override
    public Budget save(Budget model) {
        budgetValidationService.verifyValidation(model);
        budgetRepository.save(model);
        return model;
    }

    @Override
    public boolean delete(Budget model) {
        budgetValidationService.verifyValidation(model);
        budgetValidationService.verifyPositiveId(model.getId());
        budgetRepository.delete(model);
        return true;
    }

    @Override
    public Budget findById(long id) {
        budgetValidationService.verifyPositiveId(id);
        Optional<Budget> budget = budgetRepository.findById(id);
        return budget.isPresent() ? budget.get() : null;
    }

    @Override
    public Set<Budget> findAll() {
        Set<Budget> budgets = new HashSet<>();
        budgetRepository.findAll().forEach(budgets::add);
        return budgets;
    }

    @Override
    public Budget create(String name, Balance balance) {
        Budget budget = new Budget(name, balance, LocalDateTime.now().withNano(0));
        budgetValidationService.verifyValidation(budget);
        return budget;
    }
}
