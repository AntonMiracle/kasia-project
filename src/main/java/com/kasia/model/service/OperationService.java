package com.kasia.model.service;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.IntervalRuntimeException;
import com.kasia.model.*;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Set;

public interface OperationService {

    Operation createOperation(User user, Element element, Provider provider, Price price) throws ValidationException, IdInvalidRuntimeException;

    boolean addOperation(long budgetId, Operation operation) throws ValidationException, CurrenciesNotEqualsRuntimeException;

    boolean removeOperation(long budgetId, long operationId) throws ValidationException, CurrenciesNotEqualsRuntimeException;

    Set<Operation> findAllOperations(long budgetId);

    Set<Operation> findOperationsByElement(long budgetId, long elementId);

    Set<Operation> findOperationsByElementProvider(long budgetId, long providerId);

    Set<Operation> findOperationsBetweenDates(long budgetId, LocalDateTime from, LocalDateTime to) throws IntervalRuntimeException;

    Set<Operation> findOperationsBetweenPrices(long budgetId, Price from, Price to) throws IntervalRuntimeException;

    Set<Operation> findOperationsByUser(long budgetId, long userId);
}
