package com.kasia.controller;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.controller.dto.OperationsHistoryPages;
import com.kasia.model.Budget;
import com.kasia.model.Currencies;
import com.kasia.model.Statistic;
import com.kasia.model.User;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.ZoneId;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class BudgetController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private StatisticService stasticS;

    @ModelAttribute("statistic")
    public Statistic getStatistic() {
        return stasticS.get(sessionC.getBudget().getId())
                .calculate(sessionC.getUser().getZoneId());
    }

    @ModelAttribute("sortByDate")
    public Statistic.Sort getSortByDate() {
        return Statistic.Sort.DATE;
    }

    @ModelAttribute("sortByElementName")
    public Statistic.Sort getSortByElementName() {
        return Statistic.Sort.ELEMENT_NAME;
    }

    @ModelAttribute("sortByPlaceName")
    public Statistic.Sort getSortByPlaceName() {
        return Statistic.Sort.PLACE_NAME;
    }
    @ModelAttribute("sortByPrice")
    public Statistic.Sort getSortByPrice() {
        return Statistic.Sort.PRICE;
    }

    @ModelAttribute("budgetAdd")
    public BudgetAdd getBudgetAdd() {
        BudgetAdd dto = new BudgetAdd();
        dto.setUserId(sessionC.getUser().getId());
        return dto;
    }

    @ModelAttribute("operationsHistory")
    public OperationsHistoryPages getWeekOperation() {
        return sessionC.getOperationsHistoryPages();
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

    @GetMapping(U_BUDGET_OPEN + "/{id}")
    public String openBudget(@PathVariable long id) {
        if (sessionC.isUserLogin()) {
            User user = sessionC.getUser();
            Budget budget = budgetS.findById(id);
            if (user != null && budget != null) {
                if (budgetS.findOwnBudgets(user.getId()).contains(budget)) {
                    sessionC.setBudget(budget);
                    sessionC.setWeekOperationHistory();
                    return redirect(U_BUDGET);
                }
            }
        }
        return redirect(U_MAIN);
    }

    @GetMapping(U_BUDGET)
    public String openBudget() {
        if (sessionC.isBudgetOpen()) {
            sessionC.setWeekOperationHistory();
            return V_BUDGET;
        }
        return redirect(U_MAIN);
    }

    @GetMapping(U_OPERATION_WEEK_NEXT)
    public String nextWeekOperationHistory() {
        sessionC.getOperationsHistoryPages().next();
        return V_BUDGET;
    }

    @GetMapping(U_OPERATION_WEEK_PREVIOUS)
    public String previousWeekOperationHistory() {
        sessionC.getOperationsHistoryPages().previous();
        return V_BUDGET;
    }

    @GetMapping(U_BUDGET_STATISTIC)
    public String openStatistic() {
        if (sessionC.isBudgetOpen()) return V_STATISTIC;
        return redirect(U_BUDGET);
    }

    @PostMapping(U_BUDGET_STATISTIC)
    public String openStatistic(Model model, @ModelAttribute("statistic") Statistic dto) {
        ZoneId zoneId = sessionC.getUser().getZoneId();
        Statistic result = stasticS.get(sessionC.getBudget().getId());
        result.setFrom(dto.getFrom());
        result.setTo(dto.getTo());
        result.setOfElement(dto.getOfElement());
        result.setOfPlace(dto.getOfPlace());
        result.setSort(dto.getSort());
        model.addAttribute("statistic", result.calculate(zoneId));
        //add to model view
        return openStatistic();
    }
}
