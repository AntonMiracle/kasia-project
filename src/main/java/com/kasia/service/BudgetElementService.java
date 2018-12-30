package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElement;
import com.kasia.model.Element;
import com.kasia.model.ElementType;

import java.util.Set;

public interface BudgetElementService extends CRUDService<BudgetElement>{
    BudgetElement create(Budget budget);

    boolean addElement(BudgetElement budgetElement, Element element);

    boolean removeElement(BudgetElement budgetElement, Element element);

    BudgetElement getByBudgetId(long id);

    Element getByName(BudgetElement budgetElement, String name);

    Set<Element> getByElementType(BudgetElement budgetElement, ElementType type);

    boolean isElementNameUnique(BudgetElement budgetElement, String name);

}
