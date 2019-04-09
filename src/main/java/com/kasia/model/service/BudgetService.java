package com.kasia.model.service;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.*;

import java.util.Map;
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

    boolean requestConnect(long budgetId, long fromUserId, long toUserId);

    boolean isRequestConnectExist(long budgetId, long fromUserId, long toUserId);

    Set<UserConnectBudgetRequest> findRequestFrom(long fromUserId);

    Set<UserConnectBudgetRequest> findRequestTo(long toUserId);

    UserConnectBudgetRequest findUserConnectBudgetRequestById(long ucbrId);

    boolean deleteConnectionRequest(long ucbrId);

    Set<Budget> findConnectionBudget(long userId);

    boolean disconnectFromBudget(long budgetId, long userId);

    Map<User, Set<Budget>> findConnectedUsers(long budgetsOwnerUserId);

    boolean disconnectUserFromBudget(long disconnectUserId, long fromBudgetId);

    boolean isConnectedToBudget(long budgetId, long userId);
}
