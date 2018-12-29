package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.BudgetOperation;
import com.kasia.model.Operation;

public interface BudgetOperationService extends CRUDService<BudgetOperation> {
    BudgetOperation create(Budget budget);

    boolean addOperation(Budget budget, Operation operation);

    boolean removeOperation(Budget budget, Operation operation);

    BudgetOperation getByBudgetId(long id);
}
