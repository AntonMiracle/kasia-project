package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;
import com.kasia.repository.OperationRepository;
import com.kasia.service.OperationService;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OpertionServiceImp implements OperationService {
    @EJB
    private OperationRepository operationRepository;

    @Override
    public Operation create(BigDecimal amount, Article article, User user, Employer employer) throws ValidationException {
        Operation opertion = new Operation(amount, article, user, employer, LocalDateTime.now().withNano(0));
        if (!isValid(opertion)) throw new ValidationException();
        operationRepository.save(opertion);
        return operationRepository.getById(opertion.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        Operation operation = operationRepository.getById(id);
        return operation == null || operationRepository.delete(operation);
    }

    @Override
    public Operation update(Operation operation) throws ValidationException, IllegalArgumentException {
        if (!isValid(operation)) throw new ValidationException();
        if (operation.getId() == 0) throw new IllegalArgumentException();
        operationRepository.save(operation);
        return operationRepository.getById(operation.getId());
    }

    @Override
    public Operation getOperationById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return operationRepository.getById(id);
    }

    @Override
    public Set<Operation> getOperationsByArticleId(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return operationRepository.getByArticleId(id);
    }

    @Override
    public Set<Operation> getOperationsByUserId(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return operationRepository.getByUserId(id);
    }

    @Override
    public Set<Operation> getOperationsByEmployerId(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return operationRepository.getByEmployerId(id);
    }

    @Override
    public Set<Operation> getAllOperations() {
        return operationRepository.getAll();
    }
}
