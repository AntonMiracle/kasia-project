package com.kasia.service;

import com.kasia.model.*;

public interface BudgetElementProviderService extends Service<BudgetElementProvider> {
    BudgetElement create(Budget budget);

    boolean addElementProvider(BudgetElementProvider budgetElementProvider, ElementProvider elementProvider);

    boolean removeElementProvider(BudgetElementProvider budgetElementProvider, ElementProvider elementProvider);

    BudgetElement getByBudgetId(long id);

    ElementProvider getByElementProviderName(BudgetElementProvider budgetElementProvider, String name);

    boolean isElementProviderNameUnique(BudgetElementProvider budgetElementProvider, String name);

}
