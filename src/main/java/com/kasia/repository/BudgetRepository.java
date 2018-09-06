package com.kasia.repository;

import com.kasia.model.Budget;

public interface BudgetRepository {
    Budget getById(long id);

    boolean delete(Budget budget);

    boolean update(Budget budget);

    Budget save(Budget budget);

}
