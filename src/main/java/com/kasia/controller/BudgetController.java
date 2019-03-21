package com.kasia.controller;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.Budget;
import com.kasia.model.Currencies;
import com.kasia.model.User;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class BudgetController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private BudgetService budgetS;

    @ModelAttribute("budgetAdd")
    public BudgetAdd getBudgetAdd() {
        BudgetAdd dto = new BudgetAdd();
        dto.setUserId(sessionC.getUser().getId());
        return dto;
    }

    @ModelAttribute("currencies")
    public Currencies[] getCurrencies() {
        return Currencies.values();
    }

    @GetMapping(U_BUDGET_ADD)
    public String openBudgetAdd() {
        return sessionC.isUserLogin() ? V_BUDGET_ADD : redirect(U_LOGIN);
    }

    @PostMapping(U_BUDGET_SAVE)
    public String addNewBudget(@Valid @ModelAttribute BudgetAdd dto, BindingResult bResult) {
        if (bResult.hasErrors()) return openBudgetAdd();

        User user = sessionC.getUser();
        Budget budget = budgetS.save(budgetS.convert(dto));
        budgetS.setOwner(budget.getId(), user.getId());

        return redirect(U_MAIN);
    }
}
