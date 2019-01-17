package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElement;
import com.kasia.model.BudgetElementProvider;
import com.kasia.model.ElementProvider;
import com.kasia.model.service.BudgetElementProviderService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BudgetElementProviderServiceImp implements BudgetElementProviderService {
    @Override
    public BudgetElementProvider save(BudgetElementProvider model) {
        return null;
    }

    @Override
    public boolean delete(BudgetElementProvider model) {
        return false;
    }

    @Override
    public BudgetElementProvider findById(long id) {
        return null;
    }

    @Override
    public Set<BudgetElementProvider> findAll() {
        return null;
    }

    @Override
    public BudgetElement create(Budget budget) {
        return null;
    }

    @Override
    public boolean addElementProvider(BudgetElementProvider budgetElementProvider, ElementProvider elementProvider) {
        return false;
    }

    @Override
    public boolean removeElementProvider(BudgetElementProvider budgetElementProvider, ElementProvider elementProvider) {
        return false;
    }

    @Override
    public BudgetElement getByBudgetId(long id) {
        return null;
    }

    @Override
    public ElementProvider getByElementProviderName(BudgetElementProvider budgetElementProvider, String name) {
        return null;
    }

    @Override
    public boolean isElementProviderNameUnique(BudgetElementProvider budgetElementProvider, String name) {
        return false;
    }
}
