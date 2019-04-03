package com.kasia.controller;

import com.kasia.controller.dto.Settings;
import com.kasia.model.Budget;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class MainController {
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private UserService userS;
    @Autowired
    private MySessionController sessionC;

    @ModelAttribute("ownBudgets")
    public Set<Budget> getBudgets() {
        return new TreeSet<>(sessionC.isUserLogin() ? budgetS.findOwnBudgets(sessionC.getUser().getId()) : new HashSet<>());
    }

    @GetMapping(U_MAIN)
    public String openHome() {
        return sessionC.isUserLogin() ? V_MAIN : redirect(U_LOGIN);
    }

    @GetMapping(U_MAIN_SETTINGS)
    public String openSettings() {
        return V_SETTINGS;
    }

    @PostMapping(U_MAIN_SETTINGS_DELETE_BUDGET)
    public String deleteBudget(Model model, @ModelAttribute("settings") Settings dto, BindingResult bResult) {
        if (!sessionC.isUserLogin()) return redirect(U_MAIN);

        if (dto.getBudgetIdForDelete() > 0) {
            if (!sessionC.getUser().getPassword().equals(userS.cryptPassword(dto.getConfirmPassword()))) {
                model.addAttribute("confirmError", true);
                return openSettings();
            } else {
                Budget budget = budgetS.findById(dto.getBudgetIdForDelete());
                budgetS.delete(budget.getId(), sessionC.getUser().getId());
            }
        }


        return redirect(U_MAIN);
    }
}
