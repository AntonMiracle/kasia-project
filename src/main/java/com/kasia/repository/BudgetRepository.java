package com.kasia.repository;

import com.kasia.model.Budget;

import java.util.Set;

public interface BudgetRepository {
    Budget getById(long id);

    boolean delete(Budget budget);

    boolean update(Budget budget);

    Budget save(Budget budget);

    Set<Budget> getAll();
}
