package com.kasia.model.service;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
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

    Operation createOperation(User user, Element element, ElementProvider provider, Price price) throws ValidationException, IdInvalidRuntimeException;

    Element findElementByName(Budget budget, String name) throws IdInvalidRuntimeException;

    boolean isElementUnique(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    boolean addElement(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    boolean removeElement(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    Set<Element> findAllElements(Budget budget) throws IdInvalidRuntimeException;

    ElementProvider findElementProviderByName(Budget budget, String name) throws IdInvalidRuntimeException;

    boolean isElementProviderUnique(Budget budget, ElementProvider provider) throws ValidationException, IdInvalidRuntimeException;

    boolean addElementProvider(Budget budget, ElementProvider provider) throws ValidationException, IdInvalidRuntimeException;

    boolean removeElementProvider(Budget budget, ElementProvider provider) throws ValidationException, IdInvalidRuntimeException;

    Set<ElementProvider> findAllElementProviders(Budget budget) throws IdInvalidRuntimeException;

    boolean addOperation(Budget budget, Operation operation) throws ValidationException, IdInvalidRuntimeException, CurrenciesNotEqualsRuntimeException;

    boolean removeOperation(Budget budget, Operation operation) throws ValidationException, IdInvalidRuntimeException, CurrenciesNotEqualsRuntimeException;

    Set<Operation> findAllOperations(Budget budget) throws IdInvalidRuntimeException;

    Set<Operation> findOperationsByElement(Budget budget, Element element) throws ValidationException, IdInvalidRuntimeException;

    Set<Operation> findOperationsByElementProvider(Budget budget, ElementProvider provider);

    Set<Operation> findOperationsBetweenDates(Budget budget, LocalDateTime from, LocalDateTime to);

    Set<Operation> findOperationsBetweenPrices(Budget budget, Price from, Price to);

    Set<Operation> findUserOperations(Budget budget, User user);


}
