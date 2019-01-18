package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.BudgetElement;
import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.repository.BudgetElementRepository;
import com.kasia.model.service.BudgetElementService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementService;
import com.kasia.model.validation.BudgetElementValidationService;
import com.kasia.model.validation.BudgetValidationService;
import com.kasia.model.validation.ElementValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BudgetElementServiceImp implements BudgetElementService {
    @Autowired
    private BudgetElementRepository beRepository;
    @Autowired
    private BudgetElementValidationService beValidationService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private BudgetValidationService budgetValidationService;
    @Autowired
    private ElementService elementService;
    @Autowired
    private ElementValidationService elementValidationService;

    @Override
    public BudgetElement save(BudgetElement model) {
        beValidationService.verifyValidation(model);
        return beRepository.save(model);
    }

    @Override
    public boolean delete(BudgetElement model) {
        beValidationService.verifyValidation(model);
        beValidationService.verifyPositiveId(model.getId());

        beRepository.delete(model);
        budgetService.delete(model.getBudget());
        for (Element element : model.getElements()) {
            elementService.delete(element);
        }
        return true;
    }

    @Override
    public BudgetElement findById(long id) {
        beValidationService.verifyPositiveId(id);
        Optional<BudgetElement> budgetElement = beRepository.findById(id);
        return budgetElement.isPresent() ? budgetElement.get() : null;
    }

    @Override
    public Set<BudgetElement> findAll() {
        Set<BudgetElement> budgetElements = new HashSet<>();
        beRepository.findAll().forEach(budgetElements::add);
        return budgetElements;
    }

    @Override
    public BudgetElement create(Budget budget) {
        budgetValidationService.verifyValidation(budget);
        BudgetElement budgetElement = new BudgetElement(budget, new HashSet<>());
        return budgetElement;
    }

    @Override
    public boolean addElement(Budget budget, Element element) {
        verifyBeforeAddRemoveOperation(budget, element);
        BudgetElement budgetElement = findByBudgetId(budget.getId());

        if (budgetElement == null) budgetElement = create(budget);
        if (budgetElement.getElements().contains(element)) return false;

        budgetElement.getElements().add(element);
        save(budgetElement);
        return true;
    }

    private void verifyBeforeAddRemoveOperation(Budget budget, Element element) {
        elementValidationService.verifyValidation(element);
        elementValidationService.verifyPositiveId(element.getId());
        beValidationService.verifyPositiveId(budget.getId());
    }

    @Override
    public boolean removeElement(Budget budget, Element element) {
        verifyBeforeAddRemoveOperation(budget, element);
        BudgetElement budgetElement = findByBudgetId(budget.getId());
        if (budgetElement == null || !budgetElement.getElements().contains(element)) return false;
        budgetElement.getElements().remove(element);
        save(budgetElement);
        return true;
    }

    @Override
    public BudgetElement findByBudgetId(long id) {
        beValidationService.verifyPositiveId(id);
        Optional<BudgetElement> budgetElement = beRepository.findByBudgetId(id);
        return budgetElement.isPresent() ? budgetElement.get() : null;
    }

    @Override
    public Element findElementByName(Budget budget, String name) {
        beValidationService.verifyPositiveId(budget.getId());
        BudgetElement budgetElement = findByBudgetId(budget.getId());
        for (Element element : budgetElement.getElements()) {
            if (element.getName().equals(name)) return element;
        }
        return null;
    }

    @Override
    public Set<Element> findByElementType(Budget budget, ElementType type) {
        beValidationService.verifyPositiveId(budget.getId());
        BudgetElement budgetElement = findByBudgetId(budget.getId());
        Set<Element> elements = new HashSet<>();
        for (Element element : budgetElement.getElements()) {
            if (element.getType() == type) elements.add(element);
        }
        return elements;
    }

    @Override
    public boolean isElementNameUnique(Budget budget, String name) {
        beValidationService.verifyPositiveId(budget.getId());
        BudgetElement budgetElement = findByBudgetId(budget.getId());
        for (Element element : budgetElement.getElements()) {
            if (element.getName().equals(name)) return false;
        }
        return true;
    }
}
