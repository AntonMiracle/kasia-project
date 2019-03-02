package com.kasia.controller;

import com.kasia.controller.dto.AddBudgetDTO;
import com.kasia.model.Budget;
import com.kasia.model.Currencies;
import com.kasia.model.User;
import com.kasia.model.service.BalanceService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
public class BudgetController {
    @Autowired
    private AppControllerAdvice appCA;
    @Autowired
    private UserService uService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private BalanceService balanceService;

    @ModelAttribute("addBudgetDTO")
    public AddBudgetDTO getAddBudgetDTO() {
        return new AddBudgetDTO();
    }

    @ModelAttribute("currencies")
    public List<String> getAllCurrencies() {
        List<String> result = new ArrayList<>();
        for (Currencies c : Currencies.values()) {
            result.add(c.name());
        }
        return result;
    }

    @ModelAttribute("allOwnBudgets")
    public Set<Budget> getAllOwnBudget(Principal principal) {
        User user = appCA.getAuthenticationUser(principal);
        if (user != null) {
            return uService.findOwnBudgets(user.getId());
        }
        return new HashSet<>();
    }

    @GetMapping(U_BUDGET)
    public String openBudget(@ModelAttribute Budget budget) {
        System.out.println(budget == null ? "null" : budget);
        if (budget != null && budget.getId() > 0) return V_BUDGET;
        return redirect(U_BUDGET_ALL);
    }

    @GetMapping(U_BUDGET_ADD)
    public String openAddBudget() {
        return V_BUDGET_ADD;
    }

    @GetMapping(U_BUDGET_ALL)
    public String openAllBudget() {
        return V_BUDGET_ALL;
    }

    @PostMapping(U_BUDGET_SAVE)
    public String addNewBudget(Principal principal, @Valid @ModelAttribute AddBudgetDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) {
            return openAddBudget();
        }

        User user = appCA.getAuthenticationUser(principal);
        Budget budget = budgetService.createBudget(dto.getName()
                , balanceService.createBalance(dto.getBanknotes(), dto.getPenny(), dto.getCurrency()));

        budgetService.saveBudget(budget);
        uService.addBudget(user.getId(), budget.getId());

        return redirect(U_BUDGET_ALL);
    }

    @GetMapping(U_BUDGET_OPEN + "/{id}")
    public String openBudget(Principal principal, Model model, @PathVariable long id) {
        User user = appCA.getAuthenticationUser(principal);
        Budget budget = budgetService.findBudgetById(id);
        if (user != null && budget != null) {
            if (uService.findOwnBudgets(user.getId()).contains(budget) || uService.findConnectUsers(budget.getId()).contains(user)) {
                model.addAttribute("budget", budget);
                return U_BUDGET;
            }
        }
        return redirect(U_BUDGET_ALL);
    }
}
