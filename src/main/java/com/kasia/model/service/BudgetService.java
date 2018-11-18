package com.kasia.model.service;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.model.Operation;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

public interface BudgetService extends ModelService {
    Budget create(String name, BigDecimal balance, Currency currency) throws NullPointerException, ValidationException;

    boolean delete(long id) throws IllegalArgumentException;

    Budget update(Budget budget) throws IllegalArgumentException, ValidationException;

    Budget getBudgetById(long id) throws IllegalArgumentException;

    Budget addOperation(Budget budget, Operation operation) throws NullPointerException, ValidationException;

    Budget removeOperation(Budget budget, Operation operation) throws NullPointerException, ValidationException;

    Set<Operation> getOperationsByArticlesType(Budget budget, Article.Type type) throws NullPointerException;

    Set<Budget> getAllBudgets();

}
