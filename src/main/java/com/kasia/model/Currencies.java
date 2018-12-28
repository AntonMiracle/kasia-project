package com.kasia.model;

import java.util.Currency;

public enum Currencies {
    EUR(Currency.getInstance("EUR"))
    , USD(Currency.getInstance("USD"))
    , RUB(Currency.getInstance("RUB"))
    , UAH(Currency.getInstance("UAH"))
    , PLN(Currency.getInstance("PLN"));

    private final Currency currency;

    Currencies(Currency currency){
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}
