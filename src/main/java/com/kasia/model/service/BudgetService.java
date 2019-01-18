package com.kasia.model.service;

import com.kasia.model.*;

import java.time.LocalDateTime;
import java.util.Set;

public interface BudgetService {
    Budget save(Budget model);

    boolean delete(Budget model);

    Budget findById(long id);

    Set<Budget> findAll();

    Budget create(String name, Balance balance);

    boolean addOperation(Budget budget, Operation operation);

    boolean removeOperation(Budget budget, Operation operation);

    boolean addElement(Budget budget, Element element);

    boolean removeElement(Budget budget, Element element);

    boolean addElementProvider(Budget budget, ElementProvider provider);

    boolean removeElementProvider(Budget budget, ElementProvider provider);

    Set<Operation> findAllOperations(Budget budget);

    Set<Operation> findOperationsByElement(Budget budget, Element element);

    Set<Operation> findOperationByElementProvider(Budget budget, ElementProvider provider);

    Set<Operation> findOperationsBetweenDates(Budget budget, LocalDateTime from, LocalDateTime to);

    Set<Operation> findOperationsBetweenPrices(Budget budget, Price from, Price to);

    Set<Operation> findUserOperations(Budget budget, User user);


}
