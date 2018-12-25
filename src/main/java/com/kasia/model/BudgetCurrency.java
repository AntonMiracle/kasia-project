package com.kasia.model;

import java.util.Currency;

public enum BudgetCurrency {
    EUR(Currency.getInstance("EUR"))
    , USD(Currency.getInstance("USD"))
    , RUB(Currency.getInstance("RUB"))
    , UAH(Currency.getInstance("UAH"))
    , PLN(Currency.getInstance("PLN"));

    private final Currency currency;

    BudgetCurrency(Currency currency){
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}
