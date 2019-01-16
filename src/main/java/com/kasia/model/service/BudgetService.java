package com.kasia.model.service;

import com.kasia.model.Balance;
import com.kasia.model.Budget;

public interface BudgetService extends Service<Budget> {

    Budget create(String name, Balance balance);

}
