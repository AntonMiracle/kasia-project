package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.ElementProvider;

public interface ElementProviderService extends CRUDService<ElementProvider> {
    ElementProvider create(String name, String description);

    boolean isNameUnique(Budget budget, String name);

    ElementProvider getByName(Budget budget, String name);
}
