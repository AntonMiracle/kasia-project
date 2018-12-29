package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElement;
import com.kasia.model.Element;

public interface BudgetElementService extends CRUDService<BudgetElement>{
    BudgetElement create(Budget budget);

    boolean addElement(Budget budget, Element element);

    boolean removeElement(BudgetElement budget, Element element);

    BudgetElement getByBudgetId(long id);



}
