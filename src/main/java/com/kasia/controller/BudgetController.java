package com.kasia.controller;

import com.kasia.controller.dto.OperationsHistoryPages;
import com.kasia.model.Statistic;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZoneId;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
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

    @ModelAttribute("operationsHistory")
    public OperationsHistoryPages getWeekOperation() {
        return sessionC.getOperationsHistoryPages();
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
        return openStatistic();
    }
}
