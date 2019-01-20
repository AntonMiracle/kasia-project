package com.kasia.model.service.imp;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IntervalRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.*;
import com.kasia.model.service.*;
import com.kasia.model.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BudgetServiceImp implements BudgetService {
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private BudgetValidation bValidation;
    @Autowired
    private BudgetRepository bRepository;
    @Autowired
    private ElementRepository eRepository;
    @Autowired
    private ElementValidation eValidation;
    @Autowired
    private ElementProviderRepository epRepository;
    @Autowired
    private ElementProviderValidation epValidation;
    @Autowired
    private BudgetElementRepository beRepository;
    @Autowired
    private BudgetElementValidation beValidation;
    @Autowired
    private BudgetElementProviderRepository bepRepository;
    @Autowired
    private BudgetElementProviderValidation bepValidation;
    @Autowired
    private UserService uService;
    @Autowired
    private UserValidation uValidation;
    @Autowired
    private OperationRepository oRepository;
    @Autowired
    private OperationValidation oValidation;
    @Autowired
    private BudgetOperationRepository boRepository;
    @Autowired
    private BudgetOperationValidation boValidation;

    @Override
    public Budget saveBudget(Budget model) {
        bValidation.verifyValidation(model);
        return bRepository.save(model);
    }

    @Override
    public boolean deleteBudget(Budget model) {
        bValidation.verifyValidation(model);
        bValidation.verifyPositiveId(model.getId());

        warningDeleteAllInBudget(model);

        bRepository.delete(model);
        return true;
    }

    private void warningDeleteAllInBudget(Budget budget) {
        bValidation.verifyPositiveId(budget.getId());

        Set<Operation> operations = findAllOperations(budget);
        boRepository.findByBudgetId(budget.getId()).ifPresent(boRepository::delete);
        operations.forEach(oRepository::delete);

        Set<Element> elements = findAllElements(budget);
        beRepository.findByBudgetId(budget.getId()).ifPresent(beRepository::delete);
        elements.forEach(eRepository::delete);

        Set<ElementProvider> providers = findAllElementProviders(budget);
        bepRepository.findByBudgetId(budget.getId()).ifPresent(bepRepository::delete);
        providers.forEach(epRepository::delete);

        uService.findConnectUsers(budget).forEach(user -> uService.removeBudget(user, budget));
    }

    @Override
    public Budget findBudgetById(long id) {
        bValidation.verifyPositiveId(id);
        Optional<Budget> budget = bRepository.findById(id);
        return budget.isPresent() ? budget.get() : null;
    }

    @Override
    public Set<Budget> findAllBudgets() {
        Set<Budget> budgets = new HashSet<>();
        bRepository.findAll().forEach(budgets::add);
        return budgets;
    }

    @Override
    public Budget createBudget(String name, Balance balance) {
        Budget b = new Budget(name, balance, LocalDateTime.now().withNano(0));
        bValidation.verifyValidation(b);
        return b;
    }

    @Override
    public Operation createOperation(User user, Element element, ElementProvider provider, Price price) throws ValidationException {
        Operation operation = new Operation(user, element, provider, price, LocalDateTime.now().withNano(0));
        oValidation.verifyPositiveIdInside(operation);
        oValidation.verifyValidation(operation);
        return operation;
    }

    @Override
    public Element findElementByName(Budget budget, String name) {
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());

        if (optional.isPresent()) {
            for (Element element : optional.get().getElements()) {
                if (element.getName().equals(name)) return element;
            }
        }

        return null;
    }

    @Override
    public boolean isElementUnique(Budget budget, Element element) {
        bValidation.verifyPositiveId(budget.getId());
        eValidation.verifyValidation(element);

        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());

        if (!optional.isPresent() || optional.get().getElements().size() == 0) return true;
        long countExist = optional.get().getElements()
                .stream()
                .filter(element1 -> element.getName().equals(element1.getName()))
                .count();
        return countExist == 0;
    }

    @Override
    public boolean addElement(Budget budget, Element element) {
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());

        BudgetElement be = optional.orElseGet(() -> new BudgetElement(budget, new HashSet<>()));

        eValidation.verifyValidation(element);
        if (!isElementUnique(budget, element)) return false;

        eRepository.save(element);
        be.getElements().add(element);

        beValidation.verifyValidation(be);
        beRepository.save(be);

        return be.getElements().contains(element);
    }

    @Override
    public boolean removeElement(Budget budget, Element element) {
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());
        eValidation.verifyValidation(element);
        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());

        if (!optional.isPresent() || !optional.get().getElements().contains(element)) return false;

        optional.get().getElements().remove(element);
        eRepository.delete(element);

        beValidation.verifyValidation(optional.get());
        beRepository.save(optional.get());

        return !optional.get().getElements().contains(element);
    }

    @Override
    public Set<Element> findAllElements(Budget budget) {
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());
        return optional.map(BudgetElement::getElements).orElseGet(HashSet::new);
    }

    @Override
    public ElementProvider findElementProviderByName(Budget budget, String name) {
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budget.getId());

        if (optional.isPresent()) {
            for (ElementProvider provider : optional.get().getElementProviders()) {
                if (provider.getName().equals(name)) return provider;
            }
        }

        return null;
    }

    @Override
    public boolean isElementProviderUnique(Budget budget, ElementProvider provider) {
        bValidation.verifyPositiveId(budget.getId());
        epValidation.verifyValidation(provider);

        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budget.getId());

        if (!optional.isPresent() || optional.get().getElementProviders().size() == 0) return true;
        long countExist = optional.get().getElementProviders()
                .stream()
                .filter(element1 -> provider.getName().equals(element1.getName()))
                .count();
        return countExist == 0;
    }

    @Override
    public boolean addElementProvider(Budget budget, ElementProvider provider) {
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budget.getId());

        BudgetElementProvider bep = optional.orElseGet(() -> new BudgetElementProvider(budget, new HashSet<>()));

        epValidation.verifyValidation(provider);
        if (!isElementProviderUnique(budget, provider)) return false;

        epRepository.save(provider);
        bep.getElementProviders().add(provider);

        bepValidation.verifyValidation(bep);
        bepRepository.save(bep);

        return bep.getElementProviders().contains(provider);
    }

    @Override
    public boolean removeElementProvider(Budget budget, ElementProvider provider) {
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());
        epValidation.verifyValidation(provider);
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budget.getId());

        if (!optional.isPresent() || !optional.get().getElementProviders().contains(provider)) return false;

        optional.get().getElementProviders().remove(provider);
        epRepository.delete(provider);

        bepValidation.verifyValidation(optional.get());
        bepRepository.save(optional.get());

        return !optional.get().getElementProviders().contains(provider);
    }

    @Override
    public Set<ElementProvider> findAllElementProviders(Budget budget) {
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budget.getId());
        return optional.map(BudgetElementProvider::getElementProviders).orElseGet(HashSet::new);
    }

    @Override
    public boolean addOperation(Budget budget, Operation operation) {
        verifyBeforeAddRemoveOperation(budget, operation);

        BudgetOperation bo = boRepository.findByBudgetId(budget.getId())
                .orElse(new BudgetOperation(budget, new HashSet<>()));
        boValidation.verifyValidation(bo);
        oRepository.save(operation);

        updateBudgetBalanceRemoveAddOperation(true, budget, operation);
        saveBudget(budget);

        bo.getOperations().add(operation);
        boRepository.save(bo);

        return bo.getOperations().contains(operation);
    }

    private void verifyBeforeAddRemoveOperation(Budget budget, Operation operation) {
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());
        oValidation.verifyValidation(operation);
        oValidation.verifyPositiveIdInside(operation);
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
    public boolean removeOperation(Budget budget, Operation operation) {
        verifyBeforeAddRemoveOperation(budget, operation);
        oValidation.verifyPositiveId(operation.getId());

        Optional<BudgetOperation> optional = boRepository.findByBudgetId(budget.getId());
        if (!optional.isPresent() || !optional.get().getOperations().contains(operation)) return false;

        updateBudgetBalanceRemoveAddOperation(false, budget, operation);
        saveBudget(budget);

        optional.get().getOperations().remove(operation);

        boValidation.verifyValidation(optional.get());
        boRepository.save(optional.get());

        return !optional.get().getOperations().contains(operation);
    }

    @Override
    public Set<Operation> findAllOperations(Budget budget) {
        Set<Operation> operations = new HashSet<>();
        bValidation.verifyPositiveId(budget.getId());
        Optional<BudgetOperation> bo = boRepository.findByBudgetId(budget.getId());
        if (bo.isPresent() && bo.get().getOperations() != null) {
            bo.get().getOperations().forEach(operations::add);
        }
        return operations;
    }

    @Override
    public Set<Operation> findOperationsByElement(Budget budget, Element element) {
        bValidation.verifyPositiveId(budget.getId());
        eValidation.verifyValidation(element);
        eValidation.verifyPositiveId(element.getId());
        Set<Operation> result = new HashSet<>();

        findAllOperations(budget)
                .stream()
                .filter(operation -> element.equals(operation.getElement()))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsByElementProvider(Budget budget, ElementProvider provider) {
        bValidation.verifyPositiveId(budget.getId());
        epValidation.verifyValidation(provider);
        epValidation.verifyPositiveId(provider.getId());
        Set<Operation> result = new HashSet<>();

        findAllOperations(budget)
                .stream()
                .filter(operation -> provider.equals(operation.getElementProvider()))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenDates(Budget budget, LocalDateTime from, LocalDateTime to) {
        bValidation.verifyPositiveId(budget.getId());
        if (from.compareTo(to) > 0) throw new IntervalRuntimeException();

        Set<Operation> result = new HashSet<>();

        findAllOperations(budget)
                .stream()
                .filter(operation -> (operation.getCreateOn().compareTo(from) >= 0)
                        && (operation.getCreateOn().compareTo(to) <= 0))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenPrices(Budget budget, Price from, Price to) {
        bValidation.verifyPositiveId(budget.getId());
        if (from.compareTo(to) > 0) throw new IntervalRuntimeException();

        Set<Operation> result = new HashSet<>();

        findAllOperations(budget)
                .stream()
                .filter(operation -> (operation.getPrice().compareTo(from) >= 0)
                        && (operation.getPrice().compareTo(to) <= 0))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsByUser(Budget budget, User user) {
        bValidation.verifyPositiveId(budget.getId());
        uValidation.verifyValidation(user);
        uValidation.verifyPositiveId(user.getId());
        Set<Operation> result = new HashSet<>();

        findAllOperations(budget)
                .stream()
                .filter(operation -> user.equals(operation.getUser()))
                .forEach(result::add);

        return result;
    }
}
