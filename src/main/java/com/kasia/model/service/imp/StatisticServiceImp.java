package com.kasia.model.service.imp;

import com.kasia.model.Statistic;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImp implements StatisticService {
    @Autowired
    private BudgetService budgetS;

    @Override
    public Statistic get(long budgetId) {
        return new Statistic(budgetS.findAllOperations(budgetId), budgetS.findAllElements(budgetId), budgetS.findAllPlaces(budgetId));
    }


}
