package com.kasia.core.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
    private Money money;

    @Before
    public void before() {
        money = Money.valueOf(10, 99, Money.Type.EUR);
    }

    @Test
    public void checkGetMatcherFind() {
        Assert.assertTrue( Money.getMatcher("10,01 USD").find());
        Assert.assertTrue( Money.getMatcher("-10,01 EUR").find());
        Assert.assertTrue( Money.getMatcher("+10,01 PLN  ").find());
        Assert.assertTrue( Money.getMatcher("    10,01 UAH  ").find());
        Assert.assertTrue( Money.getMatcher(" 10,01   RUB  ").find());
        Assert.assertTrue( Money.getMatcher(" +10,01   USD  ").find());
        Assert.assertTrue( Money.getMatcher(" -10,01   USD  ").find());
//        System.out.println(Money.getMatcher(" - 10,01   USD  ").find());
        Assert.assertFalse( Money.getMatcher(" - 10,01   USD  ").find());
    }

    @Test
    public void checkValueOf() {
        money = Money.valueOf(100, 9, Money.Type.EUR);
        Assert.assertTrue(100 == money.getBanknotes());
        Assert.assertTrue(9 == money.getPenny());
        Assert.assertTrue(Money.Type.EUR == money.getType());
        Money money2 = Money.valueOf(money);
        Assert.assertTrue(100 == money2.getBanknotes());
        Assert.assertTrue(9 == money2.getPenny());
        Assert.assertTrue(Money.Type.EUR == money2.getType());
    }

    @Test
    public void checkValueOfString() {
        Assert.assertEquals(Money.valueOf(10, 1, Money.Type.USD), money.valueOf(" 10,01 USD"));
        Assert.assertEquals(Money.valueOf(10, 1, Money.Type.USD), money.valueOf("10,1  USD"));
        Assert.assertEquals(Money.valueOf(10, 1, Money.Type.USD), money.valueOf("+10,01  USD" ));
        Assert.assertEquals(Money.valueOf(-10, 1, Money.Type.USD), money.valueOf(" -10,01 USD"));
        Assert.assertEquals(Money.valueOf(10, 1, Money.Type.USD), money.valueOf(" 10,01 USD"));
        Assert.assertEquals(Money.valueOf(10, 1, Money.Type.USD), money.valueOf("    10,01     USD     "));

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNegativePennyThenIllegalArgumentException() {
        Money.valueOf(0, -1, Money.Type.USD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPenny100OrGreaterThenIllegalArgumentException() {
        Money.valueOf(0, 100, Money.Type.USD);
    }

    @Test
    public void getType() {
        Assert.assertEquals(Money.Type.EUR, money.getType());
    }

    @Test
    public void getBanknotes() {
        Assert.assertTrue(money.getBanknotes() == 10);
    }

    @Test
    public void getPenny() {
        Assert.assertTrue(money.getPenny() == 99);
    }


    @Test
    public void checkToString() {
        Assert.assertEquals("10,23 USD", Money.valueOf(10, 23, Money.Type.USD).toString());
        Assert.assertEquals("10,07 USD", Money.valueOf(10, 7, Money.Type.USD).toString());
        Assert.assertEquals("0,00 USD", Money.valueOf(0, 0, Money.Type.USD).toString());
        Assert.assertEquals("19923441232,02 USD", Money.valueOf(19923441232L, 2, Money.Type.USD).toString());
    }

    @Test
    public void checkEqualsAndHashCode() {
        money = Money.valueOf(10, 20, Money.Type.USD);
        Money money2 = Money.valueOf(10, 20, Money.Type.USD);
        Assert.assertEquals(money, money2);
        Assert.assertEquals(money.hashCode(), money2.hashCode());
        money = Money.valueOf(10, 20, Money.Type.EUR);
        ;
        Assert.assertNotEquals(money, money2);
        Assert.assertNotEquals(money.hashCode(), money2.hashCode());
        money = Money.valueOf(10, 99, Money.Type.USD);
        ;
        Assert.assertNotEquals(money, money2);
        Assert.assertNotEquals(money.hashCode(), money2.hashCode());
        money = Money.valueOf(23, 20, Money.Type.USD);
        ;
        Assert.assertNotEquals(money, money2);
        Assert.assertNotEquals(money.hashCode(), money2.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscount100OrGreaterThenIllegalArgumentException() {
        money.getDiscount(100.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscountNegativeThenIllegalArgumentException() {
        money.getDiscount(-1);
    }

    @Test
    public void getDiscountPrice() {
        money = Money.valueOf(100, 95, Money.Type.USD);
        Assert.assertEquals(Money.valueOf(99, 94, Money.Type.USD), money.getDiscount(1));
        Assert.assertEquals(Money.valueOf(99, 47, Money.Type.USD), money.getDiscount(1.47));
        Assert.assertEquals(Money.valueOf(67, 64, Money.Type.USD), money.getDiscount(33));
        Assert.assertEquals(Money.valueOf(1, 1, Money.Type.USD), money.getDiscount(99));
        money = Money.valueOf(537, 36, Money.Type.USD);
        Assert.assertEquals(Money.valueOf(248, 96, Money.Type.USD), money.getDiscount(53.67));
    }

    @Test
    public void calculatePlusMoney() {
        money =  Money.valueOf(12, 17, Money.Type.USD);
        Money money2 =  Money.valueOf(10, 20, Money.Type.USD);
        Assert.assertEquals( Money.valueOf(22, 37, Money.Type.USD), money.plus(money2));
        money =  Money.valueOf(99, 99, Money.Type.USD);
        Assert.assertEquals( Money.valueOf(110, 19, Money.Type.USD), money.plus(money2));
    }

    @Test
    public void whenPlusOriginalMoneyWithoutChanges() {
        money =  Money.valueOf(12, 30, Money.Type.USD);
        money.plus(money);
        Assert.assertEquals(30, money.getPenny());
        Assert.assertEquals(12, money.getBanknotes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPlusAnotherCurrencyThenIllegalArgumentException() {
        money =  Money.valueOf(1020, 45, Money.Type.USD);
        money.plus( Money.valueOf(100, 95, Money.Type.EUR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongFormatThenIllegalArgumentException() {
        Assert.assertEquals(Money.valueOf(10, 1, Money.Type.USD), money.valueOf(" - 10,01 USD"));
    }

}