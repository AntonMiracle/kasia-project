package com.kasia.controller;

import com.kasia.model.*;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.MyStringFormatter;
import com.kasia.model.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private MySessionController sessionController;
    @Autowired
    private BudgetService bService;
    @Autowired
    private OperationService oService;
    @Autowired
    private MyStringFormatter myStringFormatter;

    @ModelAttribute("user")
    public User getUser() {
        return sessionController.getUser();
    }

    @ModelAttribute("budget")
    public Budget getBudget() {
        return sessionController.getBudget();
    }

    @ModelAttribute("providers")
    public Set<Provider> getAllProviders() {
        Budget budget = sessionController.getBudget();
        if (budget != null) return bService.findAllProviders(budget.getId());
        else return new HashSet<>();
    }

    @ModelAttribute("elements")
    public Set<Element> getAllElements() {
        Budget budget = sessionController.getBudget();
        if (budget != null) return bService.findAllElements(budget.getId());
        else return new HashSet<>();
    }

    @ModelAttribute("operations")
    public Set<Operation> getAllOperations() {
        Budget budget = sessionController.getBudget();
        Set<Operation> result = new TreeSet<>();
        if (budget != null) result.addAll(oService.findAllOperations(budget.getId()));
        return result;
    }

    @ModelAttribute("myFormatter")
    public MyStringFormatter getMyFormatter() {
        return myStringFormatter;
    }
}
