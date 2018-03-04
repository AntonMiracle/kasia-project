package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;

public class MoneyTypeTest {
    @Test
    public void toStringReturnCurrencyCode() {
        Assert.assertEquals(Currency.getInstance("EUR"), Currency.getInstance(Money.Type.EUR.toString()));
        Assert.assertEquals(Currency.getInstance("PLN"), Currency.getInstance(Money.Type.PLN.toString()));
        Assert.assertEquals(Currency.getInstance("UAH"), Currency.getInstance(Money.Type.UAH.toString()));
        Assert.assertEquals(Currency.getInstance("USD"), Currency.getInstance(Money.Type.USD.toString()));
        Assert.assertEquals(Currency.getInstance("RUB"), Currency.getInstance(Money.Type.RUB.toString()));
    }

    @Test
    public void getCurrency() {
        Assert.assertEquals(Currency.getInstance("EUR"), Money.Type.EUR.getCurrency());
        Assert.assertEquals(Currency.getInstance("PLN"), Money.Type.PLN.getCurrency());
        Assert.assertEquals(Currency.getInstance("UAH"), Money.Type.UAH.getCurrency());
        Assert.assertEquals(Currency.getInstance("USD"), Money.Type.USD.getCurrency());
        Assert.assertEquals(Currency.getInstance("RUB"), Money.Type.RUB.getCurrency());
    }

    @Test
    public void getRegex() {
        Assert.assertEquals("PLN|EUR|USD|UAH|RUB", Money.Type.getRegex());
    }

    @Test
    public void getTypeOfString() {
        Assert.assertEquals(Money.Type.USD, Money.Type.typeOf("USD"));

    }
    @Test(expected = IllegalArgumentException.class)
    public void whenTypeNotExistThenThrowIllegalArgumentException() {
        Money.Type.typeOf("USDg");

    }
}