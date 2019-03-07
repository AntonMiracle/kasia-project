package com.kasia.model.service.imp;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IntervalRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.BudgetOperationRepository;
import com.kasia.model.repository.ElementProviderRepository;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.repository.OperationRepository;
import com.kasia.model.service.BalanceService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.OperationService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class OperationServiceImp implements OperationService {
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private ElementRepository eRepository;
    @Autowired
    private ElementProviderRepository epRepository;
    @Autowired
    private UserService uService;
    @Autowired
    private OperationRepository oRepository;
    @Autowired
    private BudgetOperationRepository boRepository;
    @Autowired
    private BudgetService bService;

    @Override
    public Operation createOperation(User user, Element element, Provider provider, Price price) {
        Operation operation = new Operation(user, element, provider, price, LocalDateTime.now().withNano(0));
        return operation;
    }

    private void verifyBeforeAddRemoveOperation(Budget budget, Operation operation) {
        if (budget.getBalance().getCurrencies() != operation.getPrice().getCurrencies())
            throw new CurrenciesNotEqualsRuntimeException();
    }

    private void updateBudgetBalanceRemoveAddOperation(boolean isAddOperation, Budget budget, Operation operation) {
        switch (operation.getElement().getType()) {
            case INCOME:
                if (isAddOperation) {
                    budget.setBalance(balanceService.add(budget.getBalance(), operation.getPrice()));
                } else {
                    budget.setBalance(balanceService.subtract(budget.getBalance(), operation.getPrice()));
                }
                break;
            case CONSUMPTION:
                if (isAddOperation) {
                    budget.setBalance(balanceService.subtract(budget.getBalance(), operation.getPrice()));
                } else {
                    budget.setBalance(balanceService.add(budget.getBalance(), operation.getPrice()));
                }
                break;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public boolean addOperation(long budgetId, Operation operation) {
        Budget budget = bService.findBudgetById(budgetId);
        if (budget == null || operation == null) return false;
        verifyBeforeAddRemoveOperation(budget, operation);

        BudgetOperation bo = boRepository.findByBudgetId(budget.getId())
                .orElse(new BudgetOperation(budget, new HashSet<>()));
        oRepository.save(operation);

        updateBudgetBalanceRemoveAddOperation(true, budget, operation);
        bService.saveBudget(budget);

        bo.getOperations().add(operation);
        boRepository.save(bo);

        return bo.getOperations().contains(operation);
    }

    @Override
    public boolean removeOperation(long budgetId, long operationId) {
        Operation operation = oRepository.findById(operationId).orElse(null);
        Budget budget = bService.findBudgetById(budgetId);
        if (operation == null || budget == null) return false;

        verifyBeforeAddRemoveOperation(budget, operation);

        Optional<BudgetOperation> optional = boRepository.findByBudgetId(budget.getId());
        if (!optional.isPresent() || !optional.get().getOperations().contains(operation)) return false;

        updateBudgetBalanceRemoveAddOperation(false, budget, operation);
        bService.saveBudget(budget);

        optional.get().getOperations().remove(operation);
        oRepository.delete(operation);
        boRepository.save(optional.get());

        return !optional.get().getOperations().contains(operation);
    }

    @Override
    public Set<Operation> findAllOperations(long budgetId) {
        Set<Operation> operations = new HashSet<>();
        Optional<BudgetOperation> bo = boRepository.findByBudgetId(budgetId);
        if (bo.isPresent() && bo.get().getOperations() != null) {
            operations.addAll(bo.get().getOperations());
        }
        return operations;
    }

    @Override
    public Set<Operation> findOperationsByElement(long budgetId, long elementId) {
        Element element = eRepository.findById(elementId).orElse(null);
        Set<Operation> result = new HashSet<>();
        if (element == null) return result;

        findAllOperations(budgetId)
                .stream()
                .filter(operation -> element.equals(operation.getElement()))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsByElementProvider(long budgetId, long providerId) {
        Provider provider = epRepository.findById(providerId).orElse(null);
        Set<Operation> result = new HashSet<>();
        if (provider == null) return result;

        findAllOperations(budgetId)
                .stream()
                .filter(operation -> provider.equals(operation.getProvider()))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenDates(long budgetId, LocalDateTime from, LocalDateTime to) {
        Set<Operation> result = new HashSet<>();
        if (from == null || to == null) return result;
        if (from.compareTo(to) > 0) throw new IntervalRuntimeException();


        findAllOperations(budgetId)
                .stream()
                .filter(operation -> (operation.getCreateOn().compareTo(from) >= 0)
                        && (operation.getCreateOn().compareTo(to) <= 0))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenPrices(long budgetId, Price from, Price to) {
        Set<Operation> result = new HashSet<>();
        if (from == null || to == null) return result;
        if (from.compareTo(to) > 0) throw new IntervalRuntimeException();


        findAllOperations(budgetId)
                .stream()
                .filter(operation -> (operation.getPrice().compareTo(from) >= 0)
                        && (operation.getPrice().compareTo(to) <= 0))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsByUser(long budgetId, long userId) {
        User user = uService.findUserById(userId);
        Set<Operation> result = new HashSet<>();
        if (user == null) return result;

        findAllOperations(budgetId)
                .stream()
                .filter(operation -> user.equals(operation.getUser()))
                .forEach(result::add);

        return result;
    }

}
