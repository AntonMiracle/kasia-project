package com.kasia.controller;

import com.kasia.controller.dto.Settings;
import com.kasia.model.Budget;
import com.kasia.model.OperationType;
import com.kasia.model.User;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.MyStringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private MyStringFormatter formatter;
    @Autowired
    private BudgetService budgetS;

    @ModelAttribute("isOwner")
    public boolean isOwner() {
        if (sessionC.getBudget() == null) return false;
        return budgetS.isOwner(sessionC.getBudget().getId(), sessionC.getUser().getId());
    }

    @ModelAttribute("user")
    public User getUser() {
        return sessionC.getUser();
    }

    @ModelAttribute("budget")
    public Budget getBudget() {
        return sessionC.getBudget();
    }

    @ModelAttribute("myFormatter")
    public MyStringFormatter getMyFormatter() {
        return formatter;
    }

    @ModelAttribute("income")
    public OperationType getIncome() {
        return OperationType.INCOME;
    }

    @ModelAttribute("consumption")
    public OperationType getConsumption() {
        return OperationType.CONSUMPTION;
    }

    @ModelAttribute("settings")
    public Settings getSettings() {
        Settings settings = new Settings();
        settings.setHasOwnBudget(budgetS.findOwnBudgets(sessionC.getUser().getId()).size() > 0);
        return settings;
    }
}
