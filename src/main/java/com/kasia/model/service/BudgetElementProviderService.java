package com.kasia.model.service;

import com.kasia.model.*;

public interface BudgetElementProviderService extends Service<BudgetElementProvider> {
    BudgetElementProvider create(Budget budget);

    boolean addElementProvider(Budget budget, ElementProvider elementProvider);

    boolean removeElementProvider(Budget budget, ElementProvider elementProvider);

    BudgetElementProvider findByBudgetId(long id);

    ElementProvider findElementProviderByName(Budget budget, String name);

    boolean isElementProviderNameUnique(Budget budget, String name);

}
