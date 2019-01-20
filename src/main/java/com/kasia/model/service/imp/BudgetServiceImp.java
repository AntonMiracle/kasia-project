package com.kasia.model.service.imp;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IntervalRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.*;
import com.kasia.model.service.BalanceService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean deleteBudget(long id) {
        Budget model = findBudgetById(id);
        if (model == null) return false;
        warningDeleteAllInBudget(model);

        bRepository.delete(model);
        return true;
    }

    private void warningDeleteAllInBudget(Budget budget) {
        Set<Operation> operations = findAllOperations(budget.getId());
        boRepository.findByBudgetId(budget.getId()).ifPresent(boRepository::delete);
        operations.forEach(oRepository::delete);

        Set<Element> elements = findAllElements(budget.getId());
        beRepository.findByBudgetId(budget.getId()).ifPresent(beRepository::delete);
        elements.forEach(eRepository::delete);

        Set<ElementProvider> providers = findAllElementProviders(budget.getId());
        bepRepository.findByBudgetId(budget.getId()).ifPresent(bepRepository::delete);
        providers.forEach(epRepository::delete);

        uService.findConnectUsers(budget.getId()).forEach(user -> uService.removeBudget(user.getId(), budget.getId()));
    }

    @Override
    public Budget findBudgetById(long id) {
        if (id <= 0) return null;
        Optional<Budget> budget = bRepository.findById(id);
        return budget.orElse(null);
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
    public Operation createOperation(User user, Element element, ElementProvider provider, Price price) {
        Operation operation = new Operation(user, element, provider, price, LocalDateTime.now().withNano(0));
        oValidation.verifyPositiveIdInside(operation);
        oValidation.verifyValidation(operation);
        return operation;
    }

    @Override
    public Element findElementByName(long budgetId, String name) {
        Optional<BudgetElement> optional = beRepository.findByBudgetId(budgetId);

        if (budgetId > 0 && optional.isPresent()) {
            for (Element element : optional.get().getElements()) {
                if (element.getName().equals(name)) return element;
            }
        }

        return null;
    }

    @Override
    public boolean isElementUnique(long budgetId, String elementName) {
        if (budgetId <= 0 || elementName == null) return false;

        Optional<BudgetElement> optional = beRepository.findByBudgetId(budgetId);

        if (!optional.isPresent() || optional.get().getElements().size() == 0) return true;
        long countExist = optional.get().getElements()
                .stream()
                .filter(element -> element.getName().equals(elementName))
                .count();
        return countExist == 0;
    }

    @Override
    public boolean addElement(long budgetId, Element element) {
        Budget budget = findBudgetById(budgetId);

        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());

        BudgetElement be = optional.orElseGet(() -> new BudgetElement(budget, new HashSet<>()));

        eValidation.verifyValidation(element);
        if (!isElementUnique(budget.getId(), element.getName())) return false;

        eRepository.save(element);
        be.getElements().add(element);

        beValidation.verifyValidation(be);
        beRepository.save(be);

        return be.getElements().contains(element);
    }

    @Override
    public boolean removeElement(long budgetId, long elementId) {
        Element element = eRepository.findById(elementId).orElse(null);

        if (element == null) return false;

        Optional<BudgetElement> optional = beRepository.findByBudgetId(budgetId);

        if (!optional.isPresent() || !optional.get().getElements().contains(element)) return false;

        optional.get().getElements().remove(element);
        eRepository.delete(element);

        beValidation.verifyValidation(optional.get());
        beRepository.save(optional.get());

        return !optional.get().getElements().contains(element);
    }

    @Override
    public Set<Element> findAllElements(long budgetId) {
        Optional<BudgetElement> optional = beRepository.findByBudgetId(budgetId);
        return optional.map(BudgetElement::getElements).orElseGet(HashSet::new);
    }

    @Override
    public ElementProvider findElementProviderByName(long budgetId, String name) {
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budgetId);

        if (optional.isPresent()) {
            for (ElementProvider provider : optional.get().getElementProviders()) {
                if (provider.getName().equals(name)) return provider;
            }
        }

        return null;
    }

    @Override
    public boolean isElementProviderUnique(long budgetId, String providerName) {
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budgetId);

        if (providerName == null) return false;

        if (!optional.isPresent() || optional.get().getElementProviders().size() == 0) return true;
        long countExist = optional.get().getElementProviders()
                .stream()
                .filter(provider -> provider.getName().equals(providerName))
                .count();
        return countExist == 0;
    }

    @Override
    public boolean addElementProvider(long budgetId, ElementProvider provider) {
        if(budgetId <=0) return false;

        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budgetId);

        BudgetElementProvider bep = optional
                .orElseGet(() ->
                        new BudgetElementProvider(findBudgetById(budgetId), new HashSet<>()));

        epValidation.verifyValidation(provider);
        if (!isElementProviderUnique(budgetId, provider.getName())) return false;

        epRepository.save(provider);
        bep.getElementProviders().add(provider);

        bepValidation.verifyValidation(bep);
        bepRepository.save(bep);

        return bep.getElementProviders().contains(provider);
    }

    @Override
    public boolean removeElementProvider(long budgetId, long providerId) {
        Optional<BudgetElementProvider> optionalBEP = bepRepository.findByBudgetId(budgetId);
        ElementProvider provider = epRepository.findById(providerId).orElse(null);

        if (!optionalBEP.isPresent() || !optionalBEP.get().getElementProviders().contains(provider)) return false;

        optionalBEP.get().getElementProviders().remove(provider);
        epRepository.delete(provider);

        bepValidation.verifyValidation(optionalBEP.get());
        bepRepository.save(optionalBEP.get());

        return !optionalBEP.get().getElementProviders().contains(provider);
    }

    @Override
    public Set<ElementProvider> findAllElementProviders(long budgetId) {
        Optional<BudgetElementProvider> optional = bepRepository.findByBudgetId(budgetId);
        return optional.map(BudgetElementProvider::getElementProviders).orElseGet(HashSet::new);
    }

    @Override
    public boolean addOperation(long budgetId, Operation operation) {
        Budget budget = findBudgetById(budgetId);
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
    public boolean removeOperation(long budgetId, long operationId) {
        Operation operation = oRepository.findById(operationId).orElse(null);
        Budget budget = findBudgetById(budgetId);
        if (operation == null || budget == null) return false;

        verifyBeforeAddRemoveOperation(budget, operation);

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
        ElementProvider provider = epRepository.findById(providerId).orElse(null);
        Set<Operation> result = new HashSet<>();
        if (provider == null) return result;

        findAllOperations(budgetId)
                .stream()
                .filter(operation -> provider.equals(operation.getElementProvider()))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenDates(long budgetId, LocalDateTime from, LocalDateTime to) {
        if (from.compareTo(to) > 0) throw new IntervalRuntimeException();

        Set<Operation> result = new HashSet<>();

        findAllOperations(budgetId)
                .stream()
                .filter(operation -> (operation.getCreateOn().compareTo(from) >= 0)
                        && (operation.getCreateOn().compareTo(to) <= 0))
                .forEach(result::add);

        return result;
    }

    @Override
    public Set<Operation> findOperationsBetweenPrices(long budgetId, Price from, Price to) {
        if (from.compareTo(to) > 0) throw new IntervalRuntimeException();

        Set<Operation> result = new HashSet<>();

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
