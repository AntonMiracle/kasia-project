package com.kasia.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetCurrencyTest {
    @Test
    public void availableCurrencyExist() {
        Set<String> currencies = new HashSet<>();
        currencies.add("EUR");
        currencies.add("USD");
        currencies.add("RUB");
        currencies.add("UAH");
        currencies.add("PLN");

        for (BudgetCurrency cur : BudgetCurrency.values()) {
            assertThat(currencies.contains(cur.getCurrency().toString())).isTrue();
        }
    }

}