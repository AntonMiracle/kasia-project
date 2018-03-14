package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MoneyTest {
    private Money money;
    private Money.Type type;

    @Before
    public void before() {
        money = new Money();
    }

    @Test
    public void moneyOfUSD() {
        assertThat(Money.of(type.USD).getType()).isEqualTo(type.USD);
    }

    @Test
    public void moneyOfEUR() {
        assertThat(Money.of(type.EUR).getType()).isEqualTo(type.EUR);
    }

    @Test
    public void moneyOfPLN() {
        assertThat(Money.of(type.PLN).getType()).isEqualTo(type.PLN);
    }

    @Test
    public void moneyOfUAH() {
        assertThat(Money.of(type.UAH).getType()).isEqualTo(type.UAH);
    }

    @Test
    public void moneyOfRUB() {
        assertThat(Money.of(type.RUB).getType()).isEqualTo(type.RUB);
    }

    @Test
    public void moneyOfHasZeroBanknotes() {
        assertThat(Money.of(type.PLN).getBanknotes()).isEqualTo(0);
    }

    @Test
    public void moneyOfTypeHasZeroPenny() {
        assertThat(Money.of(type.PLN).getPenny()).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyOfTypeWithNullThenIAE() {
        Money.of((Money.Type) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetTypeIsNullThenIAE() {
        money.getType();
    }

    @Test
    public void moneyOfBanknotesAndPennyInUSD() {
        money = Money.of(10, 1, type.USD);
        assertThat(money.getBanknotes()).isEqualTo(10);
        assertThat(money.getPenny()).isEqualTo(1);
        assertThat(money.getType()).isEqualTo(type.USD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasMoreThenMaxPennyThenIAE() {
        Money.of(10, Money.MAX_PENNY + 1, type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasLessThenMinPennyThenIAE() {
        Money.of(0, Money.MIN_PENNY - 1, type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasMoreThenMaxBanknotesThenIAE() {
        Money.of(Money.MAX_BANKNOTES + 1, 0, Money.Type.EUR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyHasLessThenMinBanknotesThenIAE() {
        Money.of(Money.MIN_BANKNOTES - 1, 0, Money.Type.EUR);
    }

    @Test
    public void banknotesCanBeNegative() {
        money = Money.of(-10, 87, type.PLN);
        assertThat(money.getBanknotes()).isEqualTo(-10);
    }

    @Test
    public void pennyCanBeNegativeWhenZeroBanknotes() {
        money = Money.of(0, -25, type.PLN);
        assertThat(money.getBanknotes()).isEqualTo(0);
        assertThat(money.getPenny()).isEqualTo(-25);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPennyNegativeAndBanknotesNotZeroThenIAE() {
        Money.of(10, -1, type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPennyAndBanknotesAreNegativeThenIAE() {
        Money.of(-10, -1, type.PLN);
    }

    @Test
    public void moneyFromAnotherMoney() {
        money = Money.of(Money.of(190, 10, type.PLN));
        assertThat(money.getBanknotes()).isEqualTo(190);
        assertThat(money.getPenny()).isEqualTo(10);
        assertThat(money.getType()).isEqualTo(type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyOfMoneyWithNullThenIAE() {
        Money.of((Money) null);
    }

    @Test
    public void checkToStringWithPositiveMoney() {
        assertThat(Money.of(190, 10, type.USD).toString()).isEqualTo("+190,10 USD");
    }

    @Test
    public void checkToStringWithNegativeMoney() {
        assertThat(Money.of(-190, 10, type.USD).toString()).isEqualTo("-190,10 USD");

    }

    @Test
    public void toStringForNegativeMoneyWithLocale() {
        String value = Money.of(-12, 9, type.EUR).toString(Locale.US);
        assertThat(value).isEqualTo("EUR-12.09");
    }

    @Test
    public void toStringForPositiveMoneyWithLocaleGerman() {
        String value = Money.of(1200020, 9, type.EUR).toString(Locale.GERMAN);
        assertThat(value).isEqualTo("EUR +1.200.020,09");
    }

    @Test
    public void toStringForPositiveMoneyWithLocaleFrance() {
        String value = Money.of(12, 9, type.USD).toString(Locale.FRANCE);
        assertThat(value).isEqualTo("+12,09 USD");
    }

    @Test
    public void toStringForPositiveMoneyWithLocaleUK() {
        String value = Money.of(1200020, 9, type.USD).toString(Locale.UK);
        assertThat(value).isEqualTo("USD+1,200,020.09");
    }

    @Test
    public void toStringForZeroMoneyWithLocale() {
        String value = Money.of(type.EUR).toString(Locale.US);
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
        money = Money.of(100, 95, type.USD);
        assertThat(money.discount(1)).isEqualTo(Money.of(99, 94, type.USD));
        assertThat(money.discount(1.47)).isEqualTo(Money.of(99, 47, type.USD));
        assertThat(money.discount(33)).isEqualTo(Money.of(67, 64, type.USD));
        assertThat(money.discount(99)).isEqualTo(Money.of(1, 1, type.USD));
        money = Money.of(537, 36, type.USD);
        assertThat(money.discount(53.67)).isEqualTo(Money.of(248, 96, type.USD));
    }

    @Test
    public void getDiscountNegativePrice() {
        money = Money.of(-100, 95, type.USD);
        assertThat(money.discount(1)).isEqualTo(Money.of(-99, 94, type.USD));
        money = Money.of(0, -95, type.USD);
        assertThat(money.discount(1.47)).isEqualTo(Money.of(0, -94, type.USD));
//        System.out.println(0.1+0.2-0.3);
    }

    @Test
    public void discountDoNotChangeOriginalMoney() {
        money = Money.of(12, 30, type.USD);
        Money money2 = money.discount(1);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscount100OrGreaterThenIAE() {
        Money.of(100, 95, type.USD).discount(100.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscountNegativeThenIAE() {
        Money.of(100, 95, type.USD).discount(-0.0001);
    }

    @Test
    public void moneyPlusMoney() {
        money = Money.of(12, 17, type.USD);
        Money money2 = Money.of(10, 20, type.USD);
        assertThat(money.plus(money2)).isEqualTo(Money.of(22, 37, type.USD));
        money = Money.of(99, 99, type.USD);
        assertThat(money.plus(money2)).isEqualTo(Money.of(110, 19, type.USD));
    }

    @Test
    public void plusDoNotChangeOriginalMoney() {
        money = Money.of(12, 30, type.USD);
        Money money2 = money.plus(money);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPlusDifferentCurrencyThenIAE() {
        Money.of(Money.Type.USD).plus(Money.of(type.EUR));
    }


    @Test(expected = IllegalArgumentException.class)
    public void whenPlusNullThenIAE() {
        Money.of(Money.Type.USD).plus(null);
    }


    @Test
    public void moneyMinusMoney() {
        money = Money.of(12, 17, type.USD);
        Money money2 = Money.of(2, 20, type.USD);
        assertThat(money.minus(money2)).isEqualTo(Money.of(9, 97, type.USD));
    }

    @Test
    public void plusMinusMoneyWithLowBanknotes() {
        money = Money.of(0, 10, type.USD);
        money = money.plus(Money.of(0, 20, type.USD));
        money = money.minus(Money.of(0, 30, type.USD));
        assertThat(money).isEqualTo(Money.of(type.USD));
    }

    @Test
    public void minusDoNotChangeOriginalMoney() {
        Money money = Money.of(12, 30, type.USD);
        Money money2 = money.minus(money);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusAnotherCurrencyThenIAE() {
        Money.of(Money.Type.USD).minus(Money.of(type.EUR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusNullThenIAE() {
        Money.of(Money.Type.USD).minus(null);
    }

}