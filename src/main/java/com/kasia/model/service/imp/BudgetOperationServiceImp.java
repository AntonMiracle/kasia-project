package com.kasia.model.service.imp;

import com.kasia.model.*;
import com.kasia.model.service.BudgetOperationService;
import com.kasia.model.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class BudgetOperationServiceImp implements BudgetOperationService, ValidationService<BudgetOperation> {
    @Override
    public BudgetOperation save(BudgetOperation model) {
        return null;
    }

    @Override
    public boolean delete(BudgetOperation model) {
        return false;
    }

    @Override
    public BudgetOperation findById(long id) {
        return null;
    }

    @Override
    public Set<BudgetOperation> findAll() {
        return null;
    }

    @Override
    public BudgetOperation create(Budget budget) {
        return null;
    }

    @Override
    public boolean addOperation(BudgetOperation budgetOperation, Operation operation) {
        return false;
    }

    @Override
    public boolean removeOperation(BudgetOperation budgetOperation, Operation operation) {
        return false;
    }

    @Override
    public BudgetOperation getByBudgetId(long id) {
        return null;
    }

    @Override
    public Set<Operation> getByElement(BudgetOperation budgetOperation, Element element) {
        return null;
    }

    @Override
    public Set<Operation> getByElementProvider(BudgetOperation budgetOperation, ElementProvider elementProvider) {
        return null;
    }

    @Override
    public Set<Operation> getBetweenDate(BudgetOperation budgetOperation, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public Set<Operation> getBetweenPrice(BudgetOperation budgetOperation, Price from, Price to) {
        return null;
    }
}
