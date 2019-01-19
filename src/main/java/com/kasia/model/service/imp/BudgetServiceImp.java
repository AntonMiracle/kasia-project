package com.kasia.model.service.imp;

import com.kasia.model.*;
import com.kasia.model.repository.BudgetElementProviderRepository;
import com.kasia.model.repository.BudgetElementRepository;
import com.kasia.model.repository.BudgetRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementProviderService;
import com.kasia.model.service.ElementService;
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
    private BudgetValidation bValidation;
    @Autowired
    private BudgetRepository bRepository;
    @Autowired
    private ElementService eService;
    @Autowired
    private ElementValidation eValidation;
    @Autowired
    private ElementProviderService epService;
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

    @Override
    public Budget saveBudget(Budget model) {
        bValidation.verifyValidation(model);
        return bRepository.save(model);
    }

    @Override
    public boolean deleteBudget(Budget model) {
        bValidation.verifyValidation(model);
        bValidation.verifyPositiveId(model.getId());
        bRepository.delete(model);
        //need to delete elements, providers, operations, from owner user, from connect user
        return true;
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

        eService.save(element);
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

        epService.save(provider);
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

        bepValidation.verifyValidation(optional.get());
        bepRepository.save(optional.get());

        return !optional.get().getElementProviders().contains(provider);
    }

    @Override
    public Set<ElementProvider> findAllElementProviders(Budget budget) {
        return null;
    }

    @Override
    public boolean addOperation(Budget budget, Operation operation) {
        return false;
    }

    @Override
    public boolean removeOperation(Budget budget, Operation operation) {
        return false;
    }

    @Override
    public Set<Operation> findAllOperations(Budget budget) {
        return null;
    }

    @Override
    public Set<Operation> findOperationsByElement(Budget budget, Element element) {
        return null;
    }

    @Override
    public Set<Operation> findOperationByElementProvider(Budget budget, ElementProvider provider) {
        return null;
    }

    @Override
    public Set<Operation> findOperationsBetweenDates(Budget budget, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public Set<Operation> findOperationsBetweenPrices(Budget budget, Price from, Price to) {
        return null;
    }

    @Override
    public Set<Operation> findUserOperations(Budget budget, User user) {
        return null;
    }
}
