package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.model.Operation;
import com.kasia.repository.BudgetRepository;
import com.kasia.service.BudgetService;
import com.kasia.service.ValidationService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class BudgetServiceImp implements BudgetService{
    @EJB
    private BudgetRepository budgetRepository;
    @Inject
    private ValidationService<Budget> validationService;

    @Override
    public Budget create(String name, BigDecimal balance, Currency currency) throws ValidationException, NullPointerException {
        Budget budget = new Budget(name, balance, currency, LocalDateTime.now().withNano(0));
        budget.setOperations(new HashSet<>());
        if (!validationService.isValid(budget)) throw new ValidationException();
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        Budget budget = budgetRepository.getById(id);
        return budget == null || budgetRepository.delete(budget);
    }

    @Override
    public Budget update(Budget budget) throws IllegalArgumentException, ValidationException {
        if (!(validationService.isValid(budget))) throw new ValidationException();
        if (budget.getId() == 0) throw new IllegalArgumentException();
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
    }


    @Override
    public Budget getBudgetById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return budgetRepository.getById(id);
    }

    @Override
    public Set<Budget> getAllBudgets() {
        return budgetRepository.getAll();
    }

    @Override
    public Budget addOperation(Budget budget, Operation operation) throws NullPointerException, ValidationException {
        return doOperation(true, budget, operation);
    }

    @Override
    public Budget removeOperation(Budget budget, Operation operation) throws NullPointerException, ValidationException {
        return doOperation(false, budget, operation);
    }

    private Budget doOperation(boolean isAddOperation, Budget budget, Operation operation) throws NullPointerException, ValidationException {
        if (budget == null || operation == null) throw new NullPointerException();
        if (!validationService.isValid(budget)) throw new ValidationException();

        BigDecimal nBalance = BigDecimal.ONE;
        BigDecimal cBalance = budget.getBalance();
        BigDecimal oAmount = operation.getAmount();

        if (operation.getArticle().getType() == Article.Type.INCOME) {
            nBalance = isAddOperation ? cBalance.add(oAmount) : cBalance.subtract(oAmount);
            System.out.println(nBalance);
        }
        if (operation.getArticle().getType() == Article.Type.CONSUMPTION) {
            nBalance = isAddOperation ? cBalance.subtract(oAmount) : cBalance.add(oAmount);
        }

        if (isAddOperation) {
            budget.getOperations().add(operation);
        } else {
            budget.getOperations().remove(operation);
        }
        budget.setBalance(nBalance);
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
    }

    @Override
    public Set<Operation> getOperationsByArticlesType(Budget budget, Article.Type type) throws NullPointerException {
        Set<Operation> result = new HashSet<>();
        for (Operation operation : budgetRepository.getById(budget.getId()).getOperations()) {
            if (operation.getArticle().getType() == type) {
                result.add(operation);
            }
        }
        return result;
    }
}
