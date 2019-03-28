package com.kasia.model;

import org.junit.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrenciesTest {
    @Test
    public void availableCurrencyExist() {
        Currency eur = Currency.getInstance("EUR");
        Currency rub = Currency.getInstance("RUB");
        Currency uah = Currency.getInstance("UAH");
        Currency pln = Currency.getInstance("PLN");
        Currency usd = Currency.getInstance("USD");

        assertThat(Currencies.EUR.name()).isEqualTo(eur.getCurrencyCode());
        assertThat(Currencies.EUR.currency()).isEqualTo(eur);
        assertThat(Currencies.RUB.name()).isEqualTo(rub.getCurrencyCode());
        assertThat(Currencies.RUB.currency()).isEqualTo(rub);
        assertThat(Currencies.UAH.name()).isEqualTo(uah.getCurrencyCode());
        assertThat(Currencies.UAH.currency()).isEqualTo(uah);
        assertThat(Currencies.PLN.name()).isEqualTo(pln.getCurrencyCode());
        assertThat(Currencies.PLN.currency()).isEqualTo(pln);
        assertThat(Currencies.USD.name()).isEqualTo(usd.getCurrencyCode());
        assertThat(Currencies.USD.currency()).isEqualTo(usd);

    }

}