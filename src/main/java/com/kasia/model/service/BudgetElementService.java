package com.kasia.model.service;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElement;
import com.kasia.model.Element;
import com.kasia.model.ElementType;

import java.util.Set;

public interface BudgetElementService extends Service<BudgetElement> {
    BudgetElement create(Budget budget);

    boolean addElement(Budget budget, Element element);

    boolean removeElement(Budget budget, Element element);

    BudgetElement findByBudgetId(long id);

    Element findElementByName(Budget budget, String name);

    Set<Element> findByElementType(Budget budget, ElementType type);

    boolean isElementNameUnique(Budget budget, String name);

}
