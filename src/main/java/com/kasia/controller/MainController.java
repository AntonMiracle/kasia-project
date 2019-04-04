package com.kasia.controller;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.controller.dto.Settings;
import com.kasia.model.Budget;
import com.kasia.model.Currencies;
import com.kasia.model.User;
import com.kasia.model.UserConnectBudgetRequest;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.*;

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

    @ModelAttribute("connectionBudgets")
    public Set<Budget> getConnectionBudgets() {
        return new TreeSet<>(sessionC.isUserLogin() ? budgetS.findConnectionBudget(sessionC.getUser().getId()) : new HashSet<>());
    }

    @ModelAttribute("connectionUsers")
    public Map<User, Set<Budget>> getConnectionUsers() {
        return sessionC.isUserLogin() ? budgetS.findConnectedUsers(sessionC.getUser().getId()) : new HashMap<>();
    }

    @ModelAttribute("requestTo")
    public Set<UserConnectBudgetRequest> getToRequest() {
        return sessionC.isUserLogin() ? budgetS.findRequestTo(sessionC.getUser().getId()) : new HashSet<>();
    }

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

    @GetMapping(U_MAIN)
    public String openHome() {
        if (!sessionC.isUserLogin()) redirect(U_LOGIN);
        return V_MAIN;
    }

    @GetMapping(U_MAIN_SETTINGS)
    public String openSettings() {
        if (sessionC.isUserLogin()) return V_SETTINGS;
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
                budgetS.requestConnect(dto.getBudgetIdForConnect(), fromUserId, toUserId);
            }
        }
        return redirect(U_MAIN_SETTINGS);
    }

    @GetMapping(U_MAIN_CONNECTION_ACCEPT + "/{id}")
    public String acceptConnection(@PathVariable long id) {
        UserConnectBudgetRequest ucbr = budgetS.findUserConnectBudgetRequestById(id);
        if (sessionC.isUserLogin() && ucbr != null) {
            budgetS.connect(ucbr.getBudgetId(), ucbr.getToUserId());
            budgetS.deleteConnectionRequest(id);
        }
        return redirect(U_MAIN);
    }

    @GetMapping(U_MAIN_CONNECTION_REJECT + "/{id}")
    public String rejectConnection(@PathVariable long id) {
        if (sessionC.isUserLogin()) {
            budgetS.deleteConnectionRequest(id);
        }
        return redirect(U_MAIN);
    }

    @GetMapping(U_MAIN_DISCONNECT_FROM_BUDGET + "/{id}")
    public String disconnectFromBudget(@PathVariable long id) {
        if (sessionC.isUserLogin()) {
            budgetS.disconnectFromBudget(id, sessionC.getUser().getId());
        }
        return redirect(U_MAIN);
    }

    @GetMapping(U_MAIN_DISCONNECT_USER_FROM_BUDGET + "/{userId}" + "/{budgetId}")
    public String disconnectUserFromBudget(@PathVariable long userId, @PathVariable long budgetId) {
        if (sessionC.isUserLogin()) {
            budgetS.disconnectUserFromBudget(userId, budgetId);
        }
        return redirect(U_MAIN);
    }

    @GetMapping(U_BUDGET_OPEN + "/{id}")
    public String openBudget(@PathVariable long id) {
        if (sessionC.isUserLogin()) {
            User user = sessionC.getUser();
            Budget budget = budgetS.findById(id);
            if (user != null && budget != null) {
                sessionC.setBudget(budget);
                sessionC.setWeekOperationHistory();
                return redirect(U_BUDGET);
            }
        }
        return redirect(U_MAIN);
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
