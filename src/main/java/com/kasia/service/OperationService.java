package com.kasia.service;

import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Set;

public interface OperationService extends Service {
    Operation create(BigDecimal amount, Article article, User user, Employer employer) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException;

    Operation update(Operation operation) throws ValidationException, IllegalArgumentException;

    Operation getOperationById(long id) throws IllegalArgumentException;

    Set<Operation> getOperationsByArticleId(long id) throws IllegalArgumentException;

    Set<Operation> getOperationsByUserId(long id) throws IllegalArgumentException;

    Set<Operation> getOperationsByEmployerId(long id) throws IllegalArgumentException;

    Set<Operation> getAllOperations();
}
