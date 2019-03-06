package com.kasia.controller;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class MySessionController {
    @Autowired
    private UserService uService;
    @Resource(name = "sessionUser")
    private User user;
    @Resource(name = "sessionBudget")
    private Budget budget;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user != null && user.getId() > 0) this.user = user;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        if (user != null && budget != null && budget.getId() > 0 && uService.findOwnBudgets(user.getId()).contains(budget))
            this.budget = budget;
    }

    public boolean isBudgetOpen() {
        return budget != null && budget.getId() > 0;
    }
}
