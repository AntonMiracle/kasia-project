package com.kasia.core.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Currency;
import java.util.Locale;

public class PriceTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Price price;

    @Before
    public void before() {
        price = new Price();

    }

    @Test
    public void setAndGetBanknotes() {
        int banknotes = 100;
        price.setBanknotes(banknotes);
        Assert.assertEquals(banknotes, price.getBanknotes());
    }

    @Test
    public void setAndGetPennys() {
        int pennys = 10;
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
        Assert.assertNotNull(new Price(Currency.getInstance("USD")));
    }

    @Test
    public void checkEqualsAndHashCode() {
        Price price2 = new Price(10, 20, Currency.getInstance("USD"));
        price = new Price(10, 20, Currency.getInstance("USD"));
        Assert.assertEquals(price, price2);
        Assert.assertEquals(price.hashCode(), price2.hashCode());
        price.setPenny(12);
        Assert.assertNotEquals(price, price2);
        Assert.assertNotEquals(price.hashCode(), price2.hashCode());
    }

    @Test
    public void checkToString() {
        price = new Price(10, 23, Currency.getInstance("USD"));
        Assert.assertEquals("10,23 USD", price.toString());
        price = new Price(10, 7, Currency.getInstance("USD"));
        Assert.assertEquals("10,07 USD", price.toString());
        price = new Price(0, 0, Currency.getInstance("USD"));
        Assert.assertEquals("0,00 USD", price.toString());
    }

    @Test
    public void addAnotherPrice() {
        price = new Price(12, 17, Currency.getInstance("USD"));
        Price price2 = new Price(10, 20, Currency.getInstance("USD"));
        Price price3 = new Price(22, 37, Currency.getInstance("USD"));
        price = price.add(price2);
        Assert.assertEquals(price3, price);
        price.setBanknotes(99);
        price.setPenny(99);
        price3.setBanknotes(110);
        price3.setPenny(19);
        price = price.add(price2);
        Assert.assertEquals(price3, price);
    }

    @Test
    public void addPriceDoNotChengeOriginPrice() {
        int banknotes = 12;
        int penny = 30;
        price = new Price(banknotes, penny, Currency.getInstance("USD"));
        price.add(price);
        Assert.assertEquals(penny, price.getPenny());
        Assert.assertEquals(banknotes, price.getBanknotes());
    }

    @Test
    public void whenSetNegativeBanknotesThenIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        price.setBanknotes(-1);
    }

    @Test
    public void whenSetNegativePennyThenIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        price.setPenny(-1);
    }

    @Test
    public void whenSet100OrMorePennyIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        price.setPenny(100);
    }

    @Test
    public void getDiscountPrice() {
        Price prime = new Price(100, 95, Currency.getInstance("USD"));
        double discount = 1;
        price = prime.getDiscount(discount);
        Assert.assertTrue(price.getBanknotes() == 99);
        Assert.assertTrue(price.getPenny() == 94);
        discount = 1.47;
        price = prime.getDiscount(discount);
        Assert.assertTrue(price.getBanknotes() == 99);
        Assert.assertTrue(price.getPenny() == 47);
        discount = 33;
        price = prime.getDiscount(discount);
        Assert.assertTrue(price.getBanknotes() == 67);
        Assert.assertTrue(price.getPenny() == 64);
        discount = 99;
        price = prime.getDiscount(discount);
        Assert.assertTrue(price.getBanknotes() == 1);
        Assert.assertTrue(price.getPenny() == 1);
        prime = new Price(537, 36, Currency.getInstance("USD"));
        discount = 53.67;
        price = prime.getDiscount(discount);
        Assert.assertTrue(price.getBanknotes() == 248);
        Assert.assertTrue(price.getPenny() == 96);
    }

    @Test
    public void whenDiscountNegativeThenIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        price.getDiscount(-1);
    }

    @Test
    public void whenDiscountMoreThen100ThenIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        price.getDiscount(101);
    }

    @Test
    public void whenAddAnotherCurrencyThenIllegalArgumentException() {
        Price price2 = new Price(100, 95, Currency.getInstance("UAH"));
        price = new Price(1020, 45, Currency.getInstance("USD"));
        exception.expect(IllegalArgumentException.class);
        price.add(price2);
    }
}