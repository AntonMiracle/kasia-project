package com.kasia.service.model.imp;

import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;
import com.kasia.repository.OperationRepository;
import com.kasia.service.model.OperationService;
import com.kasia.service.validation.OperationValidationService;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OperationServiceImp implements OperationService {
    @Inject
    private OperationRepository repository;
    @Inject
    private OperationValidationService validationService;

    @Override
    public Operation create(BigDecimal amount, Article article, User user, Employer employer) throws ValidationException {
        Operation operation = new Operation(amount, article, user, employer, LocalDateTime.now().withNano(0));
        if (!validationService.isValid(operation)) throw new ValidationException();
        repository.save(operation);
        return repository.getById(operation.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        Operation operation = repository.getById(id);
        return operation == null || repository.delete(operation);
    }

    @Override
    public Operation update(Operation operation) throws ValidationException, IllegalArgumentException {
        if (!validationService.isValid(operation)) throw new ValidationException();
        if (operation.getId() == 0) throw new IllegalArgumentException();
        repository.save(operation);
        return repository.getById(operation.getId());
    }

    @Override
    public Operation getOperationById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getById(id);
    }

    @Override
    public Set<Operation> getOperationsByArticleId(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getByArticleId(id);
    }

    @Override
    public Set<Operation> getOperationsByUserId(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getByUserId(id);
    }

    @Override
    public Set<Operation> getOperationsByEmployerId(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getByEmployerId(id);
    }

    @Override
    public Set<Operation> getAllOperations() {
        return repository.getAll();
    }
}
