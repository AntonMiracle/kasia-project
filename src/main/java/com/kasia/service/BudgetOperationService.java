package com.kasia.service;

import com.kasia.model.*;

import java.time.LocalDateTime;
import java.util.Set;

public interface BudgetOperationService extends CRUDService<BudgetOperation> {
    BudgetOperation create(Budget budget);

    boolean addOperation(BudgetOperation budgetOperation, Operation operation);

    boolean removeOperation(BudgetOperation budgetOperation, Operation operation);

    BudgetOperation getByBudgetId(long id);

    Set<Operation> getByElement(BudgetOperation budgetOperation, Element element);

    Set<Operation> getByElementProvider(BudgetOperation budgetOperation, ElementProvider elementProvider);

    Set<Operation> getBetweenDate(BudgetOperation budgetOperation, LocalDateTime from, LocalDateTime to);

    Set<Operation> getBetweenPrice(BudgetOperation budgetOperation, Price from, Price to);
}
