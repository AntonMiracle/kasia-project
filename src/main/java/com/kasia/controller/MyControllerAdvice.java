package com.kasia.controller;

import com.kasia.model.*;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.MyStringFormatter;
import com.kasia.model.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private OperationController operationController;

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

    @ModelAttribute("packageOperations")
    public Map<LocalDateTime, Set<Operation>> getPackageOperations() {
        Budget budget = sessionController.getBudget();
        Set<Operation> operations = new TreeSet<>();
        if (budget != null) operations.addAll(oService.findAllOperations(budget.getId()));
        Map<LocalDateTime, Set<Operation>> result = new TreeMap<>();
        if (operations.size() == 0) return result;
        return operationController.packageByOperationDay(operations);
    }

    @ModelAttribute("myFormatter")
    public MyStringFormatter getMyFormatter() {
        return myStringFormatter;
    }
}
