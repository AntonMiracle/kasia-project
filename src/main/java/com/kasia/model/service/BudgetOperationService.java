package com.kasia.model.service;

import com.kasia.model.*;

import java.time.LocalDateTime;
import java.util.Set;

public interface BudgetOperationService extends Service<BudgetOperation> {
    BudgetOperation create(Budget budget);

    boolean addOperation(Budget budget, Operation operation);

    boolean removeOperation(Budget budget, Operation operation);

    BudgetOperation findByBudgetId(long id);

    Set<Operation> findOperationsByElementName(Budget budget, String name);

    Set<Operation> findOperationByElementProviderName(Budget budget, String name);

    Set<Operation> findOperationsBetweenDate(Budget budget, LocalDateTime from, LocalDateTime to);

    Set<Operation> findOperationsBetweenPrice(Budget budget, Price from, Price to);
}
