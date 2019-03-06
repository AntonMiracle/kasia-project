package com.kasia.controller;

import com.kasia.model.Budget;
import com.kasia.model.Provider;
import com.kasia.model.User;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private MySessionController sessionController;
    @Autowired
    private BudgetService bService;

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
}
