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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.kasia.controller.ViewName.*;

@Controller
@RequestMapping(BUDGET)
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

    @GetMapping("add")
    public String openAddBudget() {
        return BUDGET_ADD;
    }

    @PostMapping("save")
    public String addNewBudget(Model model, Principal principal, @Valid @ModelAttribute AddBudgetDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) {
            return openAddBudget();
        }

        User user = appCA.getAuthenticationUser(principal);
        Budget budget = budgetService.createBudget(dto.getName()
                , balanceService.createBalance(dto.getBanknotes(), dto.getPenny(), dto.getCurrency()));

        budgetService.saveBudget(budget);
        uService.addBudget(user.getId(), budget.getId());

        return redirect(ROOT + HOME);
    }
}
