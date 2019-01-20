package com.kasia.model.service;

import com.kasia.model.Balance;
import com.kasia.model.Budget;
import com.kasia.model.Element;
import com.kasia.model.ElementProvider;

import javax.validation.ValidationException;
import java.util.Set;

public interface BudgetService {

    Budget saveBudget(Budget model) throws ValidationException;

    boolean deleteBudget(long budgetId);

    Budget findBudgetById(long id);

    Set<Budget> findAllBudgets();

    Budget createBudget(String name, Balance balance) throws ValidationException;

    Element findElementByName(long budgetId, String name);

    boolean isElementUnique(long budgetId, String elementName);

    boolean addElement(long budgetId, Element element) throws ValidationException;

    boolean removeElement(long budgetId, long elementId) throws ValidationException;

    Set<Element> findAllElements(long budgetId);

    ElementProvider findElementProviderByName(long budgetId, String name);

    boolean isElementProviderUnique(long budgetId, String providerName);

    boolean addElementProvider(long budgetId, ElementProvider provider) throws ValidationException;

    boolean removeElementProvider(long budgetId, long providerId) throws ValidationException;

    Set<ElementProvider> findAllElementProviders(long budgetId);
}
