package com.kasia.controller;

import com.kasia.model.Budget;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashSet;
import java.util.Set;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class HomeController {
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private MySessionController sessionC;

    @ModelAttribute("ownBudgets")
    public Set<Budget> getBudgets() {
        return sessionC.isUserLogin() ? budgetS.findOwnBudgets(sessionC.getUser().getId()) : new HashSet<>();
    }

    @GetMapping(U_HOME)
    public String openHome() {
        return sessionC.isUserLogin() ? V_HOME : redirect(U_LOGIN);
    }
}
