package com.kasia.model.service.imp;

import com.kasia.model.*;
import com.kasia.model.repository.BudgetOperationRepository;
import com.kasia.model.service.BudgetOperationService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.OperationService;
import com.kasia.model.validation.BudgetOperationValidationService;
import com.kasia.model.validation.BudgetValidationService;
import com.kasia.model.validation.OperationValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BudgetOperationServiceImp implements BudgetOperationService {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private BudgetValidationService budgetValidationService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private OperationValidationService operationValidationService;
    @Autowired
    private BudgetOperationRepository boRepository;
    @Autowired
    private BudgetOperationValidationService boValidationService;

    @Override
    public BudgetOperation save(BudgetOperation model) {
        boValidationService.verifyValidation(model);
        return boRepository.save(model);
    }

    @Override
    public boolean delete(BudgetOperation model) {
        boValidationService.verifyValidation(model);
        boValidationService.verifyPositiveId(model.getId());

        boRepository.delete(model);
        budgetService.delete(model.getBudget());
        model.getOperations().forEach(operationService::delete);

        return true;
    }

    @Override
    public BudgetOperation findById(long id) {
        boValidationService.verifyPositiveId(id);
        Optional<BudgetOperation> bo = boRepository.findById(id);
        return bo.isPresent() ? bo.get() : null;
    }

    @Override
    public Set<BudgetOperation> findAll() {
        Set<BudgetOperation> budgetOperations = new HashSet<>();
        boRepository.findAll().forEach(budgetOperations::add);
        return budgetOperations;
    }

    @Override
    public BudgetOperation create(Budget budget) {
        budgetValidationService.verifyValidation(budget);
        return new BudgetOperation(budget, new HashSet<>());
    }

    private BudgetOperation prepaireToAddRemoveOperation(Budget budget, Operation operation) {
        budgetValidationService.verifyValidation(budget);
        budgetValidationService.verifyPositiveId(budget.getId());

        operationValidationService.verifyValidation(operation);
        operationValidationService.verifyPositiveId(operation.getId());

        return findByBudgetId(budget.getId());
    }

    @Override
    public boolean addOperation(Budget budget, Operation operation) {
        BudgetOperation bo = prepaireToAddRemoveOperation(budget, operation);
        if (bo == null) bo = create(budget);
        if (bo.getOperations().contains(operation)) return false;

        bo.getOperations().add(operation);
        save(bo);
        return true;
    }

    @Override
    public boolean removeOperation(Budget budget, Operation operation) {
        BudgetOperation bo = prepaireToAddRemoveOperation(budget, operation);
        if (bo == null || !bo.getOperations().contains(operation)) return false;

        bo.getOperations().remove(operation);
        save(bo);
        return true;
    }

    @Override
    public BudgetOperation findByBudgetId(long id) {
        boValidationService.verifyPositiveId(id);
        Optional<BudgetOperation> bo = boRepository.findByBudgetId(id);
        return bo.isPresent() ? bo.get() : null;
    }

    @Override
    public Set<Operation> findOperationsByElementName(Budget budget, String name) {
        boValidationService.verifyPositiveId(budget.getId());
        BudgetOperation bo = findByBudgetId(budget.getId());
        Set<Operation> result = new HashSet<>();
        for (Operation operation : bo.getOperations()) {
            if (operation.getElement().getName().equals(name)) result.add(operation);
        }
        return result;
    }

    @Override
    public Set<Operation> findOperationByElementProviderName(Budget budget, String name) {
        boValidationService.verifyPositiveId(budget.getId());
        BudgetOperation bo = findByBudgetId(budget.getId());
        Set<Operation> result = new HashSet<>();
        for (Operation operation : bo.getOperations()) {
            if (operation.getElementProvider().getName().equals(name)) result.add(operation);
        }
        return null;
    }

    @Override
    public Set<Operation> findOperationsBetweenDate(Budget budget, LocalDateTime from, LocalDateTime to) {
        boValidationService.verifyPositiveId(budget.getId());
        BudgetOperation bo = findByBudgetId(budget.getId());
        Set<Operation> result = new HashSet<>();
        for (Operation operation : bo.getOperations()) {
            LocalDateTime createOn = operation.getCreateOn();
            if (createOn.compareTo(from) >= 0 && createOn.compareTo(to) <= 0) result.add(operation);
        }
        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenPrice(Budget budget, Price from, Price to) {
        boValidationService.verifyPositiveId(budget.getId());
        BudgetOperation bo = findByBudgetId(budget.getId());
        Set<Operation> result = new HashSet<>();
        for (Operation operation : bo.getOperations()) {
            Price price = operation.getPrice();
            if (price.compareTo(from) >= 0 && price.compareTo(to) <= 0) result.add(operation);
        }
        return result;
    }
}
