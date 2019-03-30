package com.kasia.controller;

import com.kasia.controller.dto.OperationDTO;
import com.kasia.controller.dto.OperationsHistoryPages;
import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class MySessionController {
    @Autowired
    private BudgetService budgetS;
    @Resource(name = "sessionUser")
    private User user;
    @Resource(name = "sessionBudget")
    private Budget budget;
    @Resource(name = "sessionWeekOperationHistory")
    private OperationsHistoryPages operationsHistoryPages;
    @Resource(name = "sessionOperationDTO")
    private OperationDTO operationAdd;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user != null && user.getId() > 0) this.user = user;
    }

    public boolean isUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !auth.getName().equals("anonymousUser");
    }

    public void setBudget(Budget budget) {
        if (isUserLogin() && budgetS.findOwnBudgets(user.getId()).contains(budget))
            this.budget = budget;
    }

    public boolean isBudgetOpen() {
        return isUserLogin() && budget != null && budget.getId() > 0;
    }

    public Budget getBudget() {
        return budget;
    }

    public OperationsHistoryPages getOperationsHistoryPages() {
        return operationsHistoryPages;
    }

    public void setWeekOperationHistory() {
        if (isUserLogin() && isBudgetOpen()) {
            this.operationsHistoryPages.setPages(budgetS.findAllOperations(getBudget().getId()));
        }

    }

    public OperationDTO getOperationAdd() {
        return operationAdd;
    }

    public void setOperationAdd(OperationDTO operationAdd) {
        this.operationAdd = operationAdd;
    }
}
