package com.kasia.repository;

import com.kasia.model.Budget;

import java.util.Set;

public interface BudgetRepository {
    void save(Budget budget);

    Budget getById(long id);

    void delete(Budget budget);

    Set<Budget> getAll();
}
