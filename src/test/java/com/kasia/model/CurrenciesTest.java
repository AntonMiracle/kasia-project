package com.kasia.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrenciesTest {
    @Test
    public void availableCurrencyExist() {
        Set<String> currencies = new HashSet<>();
        currencies.add("EUR");
        currencies.add("USD");
        currencies.add("RUB");
        currencies.add("UAH");
        currencies.add("PLN");

        for (Currencies cur : Currencies.values()) {
            assertThat(currencies.contains(cur.getCurrency().toString())).isTrue();
        }
    }

}