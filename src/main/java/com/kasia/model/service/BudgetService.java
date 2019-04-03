package com.kasia.model.service;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.Budget;
import com.kasia.model.Element;
import com.kasia.model.Operation;
import com.kasia.model.Place;

import java.util.Set;

public interface BudgetService {
    boolean setOwner(long budgetId, long userId);

    Set<Budget> findOwnBudgets(long userId);

    Budget findById(long budgetId);

    boolean isOwnBudgetNameUnique(long userId, String name);

    Budget save(Budget budget);

    Budget convert(BudgetAdd budgetAdd);

    boolean addPlace(long budgetId, long placeId);

    boolean removePlace(long budgetId, long placeId);

    boolean removeElement(long budgetId, long elementId);

    boolean addElement(long budgetId, long elementId);

    Set<Place> findAllPlaces(long budgetId);

    boolean isPlaceNameUnique(long budgetId, String name);

    boolean isElementNameUnique(long budgetId, String name);

    boolean isOwner(long budgetId, long userId);

    boolean isPlaceCanDeleted(long budgetId, long placeId);

    boolean isElementCanDeleted(long budgetId, long elementId);

    Set<Element> findAllElements(long budgetId);

    Set<Operation> findAllOperations(long budgetId);

    boolean addOperation(long budgetId, long operationId);

    boolean removeOperation(long budgetId, long operationId);

    boolean delete(long budgetId, long userId);

    boolean connect(long budgetId, long userId);

    boolean connectRequest(long budgetId, long fromUserId, long toUserId);
}
