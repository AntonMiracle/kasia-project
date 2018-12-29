package com.kasia.service;

import com.kasia.model.*;

public interface BudgetElementProviderService extends CRUDService<BudgetElementProvider> {
    BudgetElement create(Budget budget);

    boolean addElementProvider(Budget budget, ElementProvider elementProvider);

    boolean removeElementProvider(Budget budget, ElementProvider elementProvider);

    BudgetElement getByBudgetId(long id);

}
