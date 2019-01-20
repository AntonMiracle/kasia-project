package com.kasia.model.service;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.IntervalRuntimeException;
import com.kasia.model.*;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
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

    Operation createOperation(User user, Element element, ElementProvider provider, Price price) throws ValidationException, IdInvalidRuntimeException;

    boolean addOperation(long budgetId, Operation operation) throws ValidationException, CurrenciesNotEqualsRuntimeException;

    boolean removeOperation(long budgetId, long operationId) throws ValidationException, CurrenciesNotEqualsRuntimeException;

    Set<Operation> findAllOperations(long budgetId);

    Set<Operation> findOperationsByElement(long budgetId, long elementId);

    Set<Operation> findOperationsByElementProvider(long budgetId, long providerId);

    Set<Operation> findOperationsBetweenDates(long budgetId, LocalDateTime from, LocalDateTime to) throws IntervalRuntimeException;

    Set<Operation> findOperationsBetweenPrices(long budgetId, Price from, Price to) throws IntervalRuntimeException;

    Set<Operation> findOperationsByUser(long budgetId, long userId);


}
