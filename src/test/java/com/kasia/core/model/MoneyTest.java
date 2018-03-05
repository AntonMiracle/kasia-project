package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

    @Test
    public void getEURMoney() {
        assertThat(Money.of(Money.Type.EUR).getType()).isEqualTo(Money.Type.EUR);
    }

    @Test
    public void getUSDMoney() {
        assertThat(Money.of(Money.Type.USD).getType()).isEqualTo(Money.Type.USD);
    }

    @Test
    public void getRUBMoney() {
        assertThat(Money.of(Money.Type.RUB).getType()).isEqualTo(Money.Type.RUB);
    }

    @Test
    public void getUAHMoney() {
        assertThat(Money.of(Money.Type.UAH).getType()).isEqualTo(Money.Type.UAH);
    }

    @Test
    public void getPLNMoney() {
        assertThat(Money.of(Money.Type.PLN).getType()).isEqualTo(Money.Type.PLN);
    }

    @Test
    public void getMoneyByTypeHasZeroBanknotes() {
        assertThat(Money.of(Money.Type.PLN).getBanknotes()).isEqualTo(0);
    }

    @Test
    public void getMoneyByTypeHasZeroPenny() {
        assertThat(Money.of(Money.Type.PLN).getPenny()).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMoneyOfNullThenIAE() {
        Money.of((Money) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMoneyTypeOfNullThenIAE() {
        Money.of((Money.Type) null);
    }

    @Test
    public void getMoneyWithBanknotesAndPenny() {
        long banknotes = 120L;
        int penny = 87;
        Money money = Money.of(banknotes, penny, Money.Type.PLN);
        assertThat(money.getBanknotes()).isEqualTo(banknotes);
        assertThat(money.getPenny()).isEqualTo(penny);
        assertThat(money.getType()).isEqualTo(Money.Type.PLN);
    }

    @Test
    public void getMoneyWithZeroBanknotesAndNegativePenny() {
        long banknotes = 0;
        int penny = -25;
        Money money = Money.of(banknotes, penny, Money.Type.PLN);
        assertThat(money.getBanknotes()).isEqualTo(banknotes);
        assertThat(money.getPenny()).isEqualTo(penny);
        assertThat(money.getType()).isEqualTo(Money.Type.PLN);
    }

    @Test
    public void getMoneyWithNegativeBanknotes() {
        long banknotes = -10L;
        int penny = 87;
        Money money = Money.of(banknotes, penny, Money.Type.PLN);
        assertThat(money.getBanknotes()).isLessThan(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMoneyWithPositiveBanknotesAndNegativePennyThenIAE() {
        Money.of(10, -1, Money.Type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMoneyWithNegativeBanknotesAndNegativePennyThenIAE() {
        Money.of(-10, -1, Money.Type.PLN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetMoneyWith100OrGreaterPennyThenIAE() {
        Money.of(10, 100, Money.Type.PLN);
    }

    @Test
    public void getMoneyFromAnotherMoney() {
        long banknotes = 10L;
        int penny = 99;
        Money.Type type = Money.Type.PLN;
        Money money = Money.of(Money.of(banknotes, penny, type));
        assertThat(money.getBanknotes()).isEqualTo(banknotes);
        assertThat(money.getPenny()).isEqualTo(penny);
        assertThat(money.getType()).isEqualTo(type);

    }

    @Test
    public void checkToStringWithNegativeBanknotesAndPositivePenny() {
        assertThat(Money.of(-10, 76, Money.Type.USD).toString()).isEqualTo("-10,76 USD");
    }

    @Test
    public void checkToStringWithZeroBanknotesAndNegativePenny() {
        assertThat(Money.of(0, -1, Money.Type.USD).toString()).isEqualTo("-0,01 USD");
    }

    @Test
    public void checkToStringPositiveBanknotesAndZeroPenny() {
        assertThat(Money.of(10, 0, Money.Type.USD).toString()).isEqualTo("+10,00 USD");
    }

    @Test
    public void checkToStringZeroBanknotesAndZeroPenny() {
        assertThat(Money.of(0, 0, Money.Type.USD).toString()).isEqualTo("0,00 USD");
    }

    @Test
    public void checkToStringZeroBanknotesAndPositivePenny() {
        assertThat(Money.of(0, 3, Money.Type.USD).toString()).isEqualTo("+0,03 USD");
    }

    @Test
    public void moneyOfStringReturn10Banknotes11PennyEUR() {
        String representation = "10,11 EUR";
        Money money = Money.of(representation);
        assertThat(money).isEqualTo(Money.of(10, 11, Money.Type.EUR));
    }

    @Test
    public void moneyOfStringIgnoreExtraWhiteSymbol() {
        String representation = "     10,11        EUR         ";
        Money money = Money.of(representation);
        assertThat(money).isEqualTo(Money.of(10, 11, Money.Type.EUR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void moneyOfStringWithExtraPoint() {
        Money.of("10.11, EUR");
    }

    @Test(expected = IllegalArgumentException.class)
    public void moneyOfStringWithExtraComma() {
        Money.of("10,11, EUR");
    }

    @Test
    public void firstMoneyOfString10BanknotesZeroPenny() {
        assertThat(Money.of("10 EUR")).isEqualTo(Money.of(10, 0, Money.Type.EUR));
    }

    @Test
    public void moneyOfString10BanknotesAndZeroPenny() {
        assertThat(Money.of("10,00 EUR")).isEqualTo(Money.of(10, 0, Money.Type.EUR));
    }

    @Test
    public void moneyOfString10BanknotesAndZeroPennyWithLowCase() {
        assertThat(Money.of("10,00 EuR")).isEqualTo(Money.of(10, 0, Money.Type.EUR));
    }

    @Test
    public void moneyOfStringZeroBanknotesAndOnePenny() {
        assertThat(Money.of("0,01 EUR")).isEqualTo(Money.of(0, 1, Money.Type.EUR));
    }

    @Test
    public void moneyOfStringZeroBanknotesAndTenPenny() {
        assertThat(Money.of("0,1 EUR")).isEqualTo(Money.of(0, 10, Money.Type.EUR));
    }

    @Test
    public void positiveMoneyOfStringZeroBanknotesAndOnePenny() {
        assertThat(Money.of("+0,01 EUR")).isEqualTo(Money.of(0, 1, Money.Type.EUR));
    }

    @Test
    public void positiveMoneyOfStringZeroBanknotesAndTenPenny() {
        assertThat(Money.of("+0,1 EUR")).isEqualTo(Money.of(0, 10, Money.Type.EUR));
    }

    @Test
    public void negativeMoneyOfStringZeroBanknotesAndOnePenny() {
        assertThat(Money.of("-0,01 EUR")).isEqualTo(Money.of(0, -1, Money.Type.EUR));
    }

    @Test
    public void negativeMoneyOfStringTenBanknotesAndTenPenny() {
        assertThat(Money.of("-10,1 EUR")).isEqualTo(Money.of(-10, 10, Money.Type.EUR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void moneyOfStringWithNull() {
        Money.of((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void firstMoneyOfStringWithWrongFormattedArgument() {
        Money.of(" - 10.2 USD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondMoneyOfStringWithWrongFormattedArgument() {
        Money.of("  1ff0.2 USD");
    }

    @Test
    public void getDiscountPositivePrice() {
        Money money = Money.of(100, 95, Money.Type.USD);
        assertThat(money.discount(1)).isEqualTo(Money.of(99, 94, Money.Type.USD));
        assertThat(money.discount(1.47)).isEqualTo(Money.of(99, 47, Money.Type.USD));
        assertThat(money.discount(33)).isEqualTo(Money.of(67, 64, Money.Type.USD));
        assertThat(money.discount(99)).isEqualTo(Money.of(1, 1, Money.Type.USD));
        money = Money.of(537, 36, Money.Type.USD);
        assertThat(money.discount(53.67)).isEqualTo(Money.of(248, 96, Money.Type.USD));
    }

    @Test
    public void getDiscountNegativePrice() {
        Money money = Money.of(-100, 95, Money.Type.USD);
        assertThat(money.discount(1)).isEqualTo(Money.of(-99, 94, Money.Type.USD));
        money = Money.of(0, -95, Money.Type.USD);
        assertThat(money.discount(1.47)).isEqualTo(Money.of(0, -94, Money.Type.USD));
    }

    @Test
    public void whenDiscountOriginalMoneyWithoutChanges() {
        Money money = Money.of(12, 30, Money.Type.USD);
        Money money2 = money.discount(1);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscountGreater100ThenIllegalArgumentException() {
        Money.of(100, 95, Money.Type.USD).discount(100.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDiscountNegativeThenIllegalArgumentException() {
        Money.of(100, 95, Money.Type.USD).discount(-0.0001);
    }

    @Test
    public void moneyPlusMoney() {
        Money money = Money.of(12, 17, Money.Type.USD);
        Money money2 = Money.of(10, 20, Money.Type.USD);
        assertThat(money.plus(money2)).isEqualTo(Money.of(22, 37, Money.Type.USD));
        money = Money.of(99, 99, Money.Type.USD);
        assertThat(money.plus(money2)).isEqualTo(Money.of(110, 19, Money.Type.USD));
    }

    @Test
    public void whenPlusOriginalMoneyWithoutChanges() {
        Money money = Money.of(12, 30, Money.Type.USD);
        Money money2 = money.plus(money);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPlusAnotherCurrencyThenIllegalArgumentException() {
        Money.of(Money.Type.USD).plus(Money.of(Money.Type.EUR));
    }


    @Test(expected = IllegalArgumentException.class)
    public void whenPlusNullThenIllegalArgumentException() {
        Money.of(Money.Type.USD).plus(null);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Money.class).usingGetClass().suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void moneyMinusMoney() {
        Money money = Money.of(12, 17, Money.Type.USD);
        Money money2 = Money.of(2, 20, Money.Type.USD);
        assertThat(money.minus(money2)).isEqualTo(Money.of(9, 97, Money.Type.USD));
    }

    @Test
    public void whenMinusOriginalMoneyWithoutChanges() {
        Money money = Money.of(12, 30, Money.Type.USD);
        Money money2 = money.minus(money);
        assertThat(money).isNotSameAs(money2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusAnotherCurrencyThenIllegalArgumentException() {
        Money.of(Money.Type.USD).minus(Money.of(Money.Type.EUR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinusNullThenIllegalArgumentException() {
        Money.of(Money.Type.USD).minus(null);
    }
}