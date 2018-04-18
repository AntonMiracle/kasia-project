package com.kasia.core.model;

public class MoneyTest {
    /**
    private Money money;

    @Before
    public void before() {
        money = new Money();
    }

    @Test
    public void moneyOfUSD() {
        assertThat(of(Type.USD).getType()).isEqualTo(Type.USD);
    }

    @Test
    public void moneyOfEUR() {
        assertThat(of(Type.EUR).getType()).isEqualTo(Type.EUR);
    }

    @Test
    public void moneyOfPLN() {
        assertThat(of(Type.PLN).getType()).isEqualTo(Type.PLN);
    }

    @Test
    public void moneyOfUAH() {
        assertThat(of(Type.UAH).getType()).isEqualTo(Type.UAH);
    }

    @Test
    public void moneyOfRUB() {
        assertThat(of(Type.RUB).getType()).isEqualTo(Type.RUB);
    }

    @Test
    public void moneyOfHasZeroBanknotes() {
        assertThat(of(Type.PLN).getBanknotes()).isEqualTo(0);
    }

    @Test
    public void moneyOfTypeHasZeroPenny() {
        assertThat(of(Type.PLN).getPenny()).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyOfTypeWithNullThenIAE() {
        of((Type) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetTypeIsNullThenIAE() {
        money.getType();
    }

    @Test
    public void moneyOfBanknotesAndPennyInUSD() {
        money = of(10, 1, Type.USD);
        assertThat(money.getBanknotes()).isEqualTo(10);
        assertThat(money.getPenny()).isEqualTo(1);
        assertThat(money.getType()).isEqualTo(Type.USD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasMoreThenMaxPennyThenIAE() {
        of(10, MAX_PENNY + 1, Type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasLessThenMinPennyThenIAE() {
        of(0, MIN_PENNY - 1, Type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasMoreThenMaxBanknotesThenIAE() {
        of(MAX_BANKNOTES + 1, 0, Type.EUR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasLessThenMinBanknotesThenIAE() {
        of(MIN_BANKNOTES - 1, 0, Type.EUR);
    }

    @Test
    public void banknotesCanBeNegative() {
        money = of(-10, 87, Type.PLN);
        assertThat(money.getBanknotes()).isEqualTo(-10);
    }

    @Test
    public void pennyCanBeNegativeWhenZeroBanknotes() {
        money = of(0, -25, Type.PLN);
        assertThat(money.getBanknotes()).isEqualTo(0);
        assertThat(money.getPenny()).isEqualTo(-25);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPennyNegativeAndBanknotesNotZeroThenIAE() {
        of(10, -1, Type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPennyAndBanknotesAreNegativeThenIAE() {
        of(-10, -1, Type.PLN);
    }

    @Test
    public void moneyFromAnotherMoney() {
        money = of(of(190, 10, Type.PLN));
        assertThat(money.getBanknotes()).isEqualTo(190);
        assertThat(money.getPenny()).isEqualTo(10);
        assertThat(money.getType()).isEqualTo(Type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyOfMoneyWithNullThenIAE() {
        of((Money) null);
    }

    @Test
    public void checkToStringWithPositiveMoney() {
        assertThat(of(190, 10, Type.USD).toString()).isEqualTo("+190,10 USD");
    }

    @Test
    public void checkToStringWithNegativeMoney() {
        assertThat(of(-190, 10, Type.USD).toString()).isEqualTo("-190,10 USD");

    }

    @Test
    public void toStringForNegativeMoneyWithLocale() {
        String value = of(-12, 9, Type.EUR).toString(Locale.US);
        assertThat(value).isEqualTo("EUR-12.09");
    }

    @Test
    public void toStringForPositiveMoneyWithLocaleGerman() {
        String value = of(1200020, 9, Type.EUR).toString(Locale.GERMAN);
        assertThat(value).isEqualTo("EUR +1.200.020,09");
    }

    @Test
    public void toStringForPositiveMoneyWithLocaleFrance() {
        String value = of(12, 9, Type.USD).toString(Locale.FRANCE);
        assertThat(value).isEqualTo("+12,09 USD");
    }

    @Test
    public void toStringForPositiveMoneyWithLocaleUK() {
        String value = of(1200020, 9, Type.USD).toString(Locale.UK);
        assertThat(value).isEqualTo("USD+1,200,020.09");
    }

    @Test
    public void toStringForZeroMoneyWithLocale() {
        String value = of(Type.EUR).toString(Locale.US);
        assertThat(value).isEqualTo("EUR+0.00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenToStringWithNullThenIAE() {
        money.toString(null);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Money.class).usingGetClass().suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void getDiscountPositivePrice() {
        money = of(100, 95, Type.USD);
        assertThat(money.discount(1)).isEqualTo(of(99, 94, Type.USD));
        assertThat(money.discount(1.47)).isEqualTo(of(99, 47, Type.USD));
        assertThat(money.discount(33)).isEqualTo(of(67, 64, Type.USD));
        assertThat(money.discount(99)).isEqualTo(of(1, 1, Type.USD));
        money = of(537, 36, Type.USD);
        assertThat(money.discount(53.67)).isEqualTo(of(248, 96, Type.USD));
    }

    @Test
    public void getDiscountNegativePrice() {
        money = of(-100, 95, Type.USD);
        assertThat(money.discount(1)).isEqualTo(of(-99, 94, Type.USD));
        money = of(0, -95, Type.USD);
        assertThat(money.discount(1.47)).isEqualTo(of(0, -94, Type.USD));
//        System.out.println(0.1+0.2-0.3);
    }

    @Test
    public void discountDoNotChangeOriginalMoney() {
        money = of(12, 30, Type.USD);
        Money money2 = money.discount(1);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscount100OrGreaterThenIAE() {
        of(100, 95, Type.USD).discount(100.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscountNegativeThenIAE() {
        of(100, 95, Type.USD).discount(-0.0001);
    }

    @Test
    public void moneyPlusMoney() {
        money = of(12, 17, Type.USD);
        Money money2 = of(10, 20, Type.USD);
        assertThat(money.plus(money2)).isEqualTo(of(22, 37, Type.USD));
        money = of(99, 99, Type.USD);
        assertThat(money.plus(money2)).isEqualTo(of(110, 19, Type.USD));
    }

    @Test
    public void plusDoNotChangeOriginalMoney() {
        money = of(12, 30, Type.USD);
        Money money2 = money.plus(money);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPlusDifferentCurrencyThenIAE() {
        of(Type.USD).plus(of(Type.EUR));
    }


    @Test(expected = IllegalArgumentException.class)
    public void whenPlusNullThenIAE() {
        of(Type.USD).plus(null);
    }


    @Test
    public void moneyMinusMoney() {
        money = of(12, 17, Type.USD);
        Money money2 = of(2, 20, Type.USD);
        assertThat(money.minus(money2)).isEqualTo(of(9, 97, Type.USD));
    }

    @Test
    public void plusMinusMoneyWithLowPenny() {
        money = of(0, 10, Type.USD);
        money = money.plus(of(0, 20, Type.USD));
        money = money.minus(of(0, 30, Type.USD));
        assertThat(money).isEqualTo(of(Type.USD));
    }

    @Test
    public void minusDoNotChangeOriginalMoney() {
        Money money = of(12, 30, Type.USD);
        Money money2 = money.minus(money);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusAnotherCurrencyThenIAE() {
        of(Type.USD).minus(of(Type.EUR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusNullThenIAE() {
        of(Type.USD).minus(null);
    }
*/
}