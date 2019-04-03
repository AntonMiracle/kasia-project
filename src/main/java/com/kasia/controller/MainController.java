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
        if (budgetS.findOwnBudgets(sessionC.getUser().getId()).size() > 0) return V_SETTINGS;
        else return redirect(U_MAIN);
    }

    @PostMapping(U_MAIN_SETTINGS_DELETE_BUDGET)
    public String deleteBudget(Model model, @ModelAttribute("settings") Settings dto, BindingResult bResult) {
        if (!sessionC.isUserLogin()) return redirect(U_MAIN);

        if (dto.getBudgetIdForDelete() > 0 && dto.getConfirmDelete().length() > 0) {
            if (!sessionC.getUser().getPassword().equals(userS.cryptPassword(dto.getConfirmDelete()))) {
                model.addAttribute("confirmDeleteError", true);
                return openSettings();
            }
            budgetS.delete(dto.getBudgetIdForDelete(), sessionC.getUser().getId());
        }
        if (dto.getBudgetIdForConnect() > 0 && dto.getConfirmConnect().length() > 0) {
            if (!sessionC.getUser().getPassword().equals(userS.cryptPassword(dto.getConfirmConnect()))) {
                model.addAttribute("confirmConnectError", true);
                return openSettings();
            }
            if (!userS.isEmailUnique(dto.getEmailToConnect()) && !sessionC.getUser().getEmail().equals(dto.getEmailToConnect())) {
                long fromUserId = sessionC.getUser().getId();
                long toUserId = userS.findByEmail(dto.getEmailToConnect()).getId();
                budgetS.connectRequest(dto.getBudgetIdForConnect(), fromUserId, toUserId);
            }
        }


        return redirect(U_MAIN_SETTINGS);
    }
}
