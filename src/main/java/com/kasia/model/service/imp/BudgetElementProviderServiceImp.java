package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElementProvider;
import com.kasia.model.ElementProvider;
import com.kasia.model.repository.BudgetElementProviderRepository;
import com.kasia.model.service.BudgetElementProviderService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementProviderService;
import com.kasia.model.validation.BudgetElementProviderValidationService;
import com.kasia.model.validation.BudgetValidationService;
import com.kasia.model.validation.ElementProviderValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BudgetElementProviderServiceImp implements BudgetElementProviderService {
    @Autowired
    private BudgetElementProviderRepository bepRepository;
    @Autowired
    private BudgetElementProviderValidationService bepValidationService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private BudgetValidationService budgetValidationService;
    @Autowired
    private ElementProviderService providerService;
    @Autowired
    private ElementProviderValidationService providerValidationService;

    @Override
    public BudgetElementProvider save(BudgetElementProvider model) {
        bepValidationService.verifyValidation(model);
        return bepRepository.save(model);
    }

    @Override
    public boolean delete(BudgetElementProvider model) {
        bepValidationService.verifyValidation(model);
        bepValidationService.verifyPositiveId(model.getId());

        bepRepository.delete(model);
        budgetService.delete(model.getBudget());
        for (ElementProvider provider : model.getElementProviders()) {
            providerService.delete(provider);
        }

        return true;
    }

    @Override
    public BudgetElementProvider findById(long id) {
        bepValidationService.verifyPositiveId(id);
        Optional<BudgetElementProvider> bep = bepRepository.findById(id);
        return bep.isPresent() ? bep.get() : null;
    }

    @Override
    public Set<BudgetElementProvider> findAll() {
        Set<BudgetElementProvider> bep = new HashSet<>();
        bepRepository.findAll().forEach(bep::add);
        return bep;
    }

    @Override
    public BudgetElementProvider create(Budget budget) {
        budgetValidationService.verifyValidation(budget);
        BudgetElementProvider bep = new BudgetElementProvider(budget, new HashSet<>());
        return bep;
    }

    @Override
    public boolean addElementProvider(Budget budget, ElementProvider elementProvider) {
        providerValidationService.verifyValidation(elementProvider);
        providerValidationService.verifyPositiveId(elementProvider.getId());

        BudgetElementProvider bep = findByBudgetId(budget.getId());
        if (bep == null) bep = create(budget);

        if (bep.getElementProviders().contains(elementProvider)) return false;
        bep.getElementProviders().add(elementProvider);

        save(bep);
        return true;
    }

    @Override
    public boolean removeElementProvider(Budget budget, ElementProvider elementProvider) {
        providerValidationService.verifyValidation(elementProvider);
        providerValidationService.verifyPositiveId(elementProvider.getId());

        BudgetElementProvider bep = findByBudgetId(budget.getId());
        if (bep == null || !bep.getElementProviders().contains(elementProvider)) return true;
        bep.getElementProviders().remove(elementProvider);

        save(bep);
        return true;
    }

    @Override
    public BudgetElementProvider findByBudgetId(long id) {
        bepValidationService.verifyPositiveId(id);
        Optional<BudgetElementProvider> bep = bepRepository.findByBudgetId(id);
        return bep.isPresent() ? bep.get() : null;
    }

    @Override
    public ElementProvider findElementProviderByName(Budget budget, String name) {
        budgetValidationService.verifyPositiveId(budget.getId());
        BudgetElementProvider bep = findByBudgetId(budget.getId());

        if (bep != null) {
            for (ElementProvider provider : bep.getElementProviders()) {
                if (provider.getName().equals(name)) return provider;
            }
        }

        return null;
    }

    @Override
    public boolean isElementProviderNameUnique(Budget budget, String name) {
        return findElementProviderByName(budget, name) == null;
    }
}
