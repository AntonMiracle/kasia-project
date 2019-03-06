package com.kasia.model.service.imp;

import com.kasia.model.*;
import com.kasia.model.repository.*;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.OperationService;
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
    private BudgetRepository bRepository;
    @Autowired
    private ElementRepository eRepository;
    @Autowired
    private ElementProviderRepository epRepository;
    @Autowired
    private BudgetElementRepository beRepository;
    @Autowired
    private BudgetElementValidation beValidation;
    @Autowired
    private BudgetElementProviderRepository bepRepository;
    @Autowired
    private BudgetProviderValidation bepValidation;
    @Autowired
    private UserService uService;
    @Autowired
    private OperationRepository oRepository;
    @Autowired
    private BudgetOperationRepository boRepository;
    @Autowired
    private OperationService oService;

    @Override
    public Budget saveBudget(Budget model) {
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
        Set<Operation> operations = oService.findAllOperations(budget.getId());
        boRepository.findByBudgetId(budget.getId()).ifPresent(boRepository::delete);
        operations.forEach(oRepository::delete);

        Set<Element> elements = findAllElements(budget.getId());
        beRepository.findByBudgetId(budget.getId()).ifPresent(beRepository::delete);
        elements.forEach(eRepository::delete);

        Set<Provider> providers = findAllProviders(budget.getId());
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
        return b;
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
        if (budgetId <= 0 || element == null) return false;
        Budget budget = findBudgetById(budgetId);

        Optional<BudgetElement> optional = beRepository.findByBudgetId(budget.getId());

        BudgetElement be = optional.orElseGet(() -> new BudgetElement(budget, new HashSet<>()));

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
    public boolean isProviderUnique(long budgetId, String providerName) {
        Optional<BudgetProvider> optional = bepRepository.findByBudgetId(budgetId);

        if (providerName == null) return false;

        if (!optional.isPresent() || optional.get().getProviders().size() == 0) return true;
        long countExist = optional.get().getProviders()
                .stream()
                .filter(provider -> provider.getName().equals(providerName))
                .count();
        return countExist == 0;
    }

    @Override
    public Provider findProviderByName(long budgetId, String name) {
        Optional<BudgetProvider> optional = bepRepository.findByBudgetId(budgetId);

        if (optional.isPresent()) {
            for (Provider provider : optional.get().getProviders()) {
                if (provider.getName().equals(name)) return provider;
            }
        }

        return null;
    }

    @Override
    public boolean addProvider(long budgetId, Provider provider) {
        if (budgetId <= 0 || provider == null) return false;

        Optional<BudgetProvider> optional = bepRepository.findByBudgetId(budgetId);

        BudgetProvider bep = optional
                .orElseGet(() ->
                        new BudgetProvider(findBudgetById(budgetId), new HashSet<>()));

        if (!isProviderUnique(budgetId, provider.getName())) return false;

        epRepository.save(provider);
        bep.getProviders().add(provider);

        bepValidation.verifyValidation(bep);
        bepRepository.save(bep);

        return bep.getProviders().contains(provider);
    }

    @Override
    public boolean removeProvider(long budgetId, long providerId) {
        Optional<BudgetProvider> optionalBEP = bepRepository.findByBudgetId(budgetId);
        Provider provider = epRepository.findById(providerId).orElse(null);

        if (!optionalBEP.isPresent() || !optionalBEP.get().getProviders().contains(provider)) return false;

        optionalBEP.get().getProviders().remove(provider);
        epRepository.delete(provider);

        bepValidation.verifyValidation(optionalBEP.get());
        bepRepository.save(optionalBEP.get());

        return !optionalBEP.get().getProviders().contains(provider);
    }

    @Override
    public Set<Provider> findAllProviders(long budgetId) {
        Optional<BudgetProvider> optional = bepRepository.findByBudgetId(budgetId);
        return optional.map(BudgetProvider::getProviders).orElseGet(HashSet::new);
    }
}
