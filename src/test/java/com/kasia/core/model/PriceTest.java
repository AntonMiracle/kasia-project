package com.kasia.core.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;
import java.util.Locale;

public class PriceTest {
    private Price price;

    @Before
    public void before() {
        price = new Price();
    }

    @Test
    public void setAndGetBanknotes() {
        Integer banknotes = 100;
        price.setBanknotes(banknotes);
        Assert.assertEquals(banknotes, price.getBanknotes());
    }

    @Test
    public void setAndGetPennys() {
        Integer pennys = 10;
        price.setPenny(pennys);
        Assert.assertEquals(pennys, price.getPenny());
    }

    @Test
    public void setAndGetCurrency() {
        Currency currency = Currency.getInstance(Locale.US);
        price.setCurrency(currency);
        Assert.assertEquals(currency, price.getCurrency());
    }

    @Test
    public void checkConstructores() {
        Assert.assertNotNull(new Price(10, 20, Currency.getInstance("USD")));
    }

    @Test
    public void checkEqualsAndHashCode() {
        Price price2 = new Price(10, 20, Currency.getInstance("USD"));
        price = new Price(10, 20, Currency.getInstance("USD"));
        Assert.assertEquals(price, price2);
        price.setPenny(12);
        Assert.assertNotEquals(price, price2);
    }

}