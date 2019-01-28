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

import static com.kasia.controller.ControllerUrl.BUDGET;
import static com.kasia.controller.ControllerUrl.HOME;

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

    @GetMapping
    public String openAddBudget(Model model) {
        model.addAttribute("openAddBudget", "openAddBudget");
        return HOME;
    }

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

    @PostMapping("addNew")
    public String addNewBudget(Model model, Principal principal, @Valid @ModelAttribute AddBudgetDTO dto, BindingResult bResult) {
        System.out.println(dto);

        if (bResult.hasErrors()) {
            return openAddBudget(model);
        }

        User user = appCA.getAuthenticationUser(principal);
        Budget budget = budgetService.createBudget(dto.getName()
                , balanceService.createBalance(dto.getBanknotes(), dto.getPenny(), dto.getCurrency()));

        budgetService.saveBudget(budget);
        uService.addBudget(user.getId(), budget.getId());

        System.out.println(budget);
        return HOME;
    }
}
