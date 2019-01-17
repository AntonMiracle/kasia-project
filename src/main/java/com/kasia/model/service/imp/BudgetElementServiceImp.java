package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElement;
import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.service.BudgetElementService;
import com.kasia.model.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BudgetElementServiceImp implements BudgetElementService, ValidationService<BudgetElement> {
    @Override
    public BudgetElement save(BudgetElement model) {
        return null;
    }

    @Override
    public boolean delete(BudgetElement model) {
        return false;
    }

    @Override
    public BudgetElement findById(long id) {
        return null;
    }

    @Override
    public Set<BudgetElement> findAll() {
        return null;
    }

    @Override
    public BudgetElement create(Budget budget) {
        return null;
    }

    @Override
    public boolean addElement(BudgetElement budgetElement, Element element) {
        return false;
    }

    @Override
    public boolean removeElement(BudgetElement budgetElement, Element element) {
        return false;
    }

    @Override
    public BudgetElement getByBudgetId(long id) {
        return null;
    }

    @Override
    public Element getByName(BudgetElement budgetElement, String name) {
        return null;
    }

    @Override
    public Set<Element> getByElementType(BudgetElement budgetElement, ElementType type) {
        return null;
    }

    @Override
    public boolean isElementNameUnique(BudgetElement budgetElement, String name) {
        return false;
    }
}
