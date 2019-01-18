package com.kasia.model.service;

import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.*;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Set;

public interface BudgetService {

    Budget saveBudget(Budget model) throws ValidationException;

    boolean deleteBudget(Budget model) throws ValidationException, IdInvalidRuntimeException;

    Budget findBudgetById(long id) throws IdInvalidRuntimeException;

    Set<Budget> findAllBudgets();

    Budget createBudget(String name, Balance balance) throws ValidationException;

    boolean addOperation(Budget budget, Operation operation);

    boolean removeOperation(Budget budget, Operation operation);

    Set<Operation> findAllOperations(Budget budget);

    Element findElementByName(Budget budget, String name) throws IdInvalidRuntimeException;

    boolean isElementUnique(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    boolean addElement(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    boolean removeElement(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    Set<Element> findAllElements(Budget budget);

    ElementProvider findElementProviderByName(Budget budget, String name);

    boolean isElementProviderUnique(Budget budget, ElementProvider provider);

    boolean addElementProvider(Budget budget, ElementProvider provider);

    boolean removeElementProvider(Budget budget, ElementProvider provider);

    Set<Element> findAllElementProviders(Budget budget);

    Set<Operation> findOperationsByElement(Budget budget, Element element);

    Set<Operation> findOperationByElementProvider(Budget budget, ElementProvider provider);

    Set<Operation> findOperationsBetweenDates(Budget budget, LocalDateTime from, LocalDateTime to);

    Set<Operation> findOperationsBetweenPrices(Budget budget, Price from, Price to);

    Set<Operation> findUserOperations(Budget budget, User user);


}
