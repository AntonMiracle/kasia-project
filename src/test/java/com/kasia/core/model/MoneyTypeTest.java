package com.kasia.core.model;

import org.junit.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTypeTest {
    @Test
    public void EURtoStringReturnCurrencyCode() {
        assertThat(Money.Type.EUR.toString()).isEqualTo(Currency.getInstance("EUR").getCurrencyCode());
    }

    @Test
    public void PLNtoStringReturnCurrencyCode() {
        assertThat(Money.Type.PLN.toString()).isEqualTo(Currency.getInstance("PLN").getCurrencyCode());
    }

    @Test
    public void UAHtoStringReturnCurrencyCode() {
        assertThat(Money.Type.UAH.toString()).isEqualTo(Currency.getInstance("UAH").getCurrencyCode());
    }

    @Test
    public void USDtoStringReturnCurrencyCode() {
        assertThat(Money.Type.USD.toString()).isEqualTo(Currency.getInstance("USD").getCurrencyCode());
    }

    @Test
    public void RUBtoStringReturnCurrencyCode() {
        assertThat(Money.Type.RUB.toString()).isEqualTo(Currency.getInstance("RUB").getCurrencyCode());
    }

    @Test
    public void getCurrencyReturnEURCurrency() {
        assertThat(Money.Type.EUR.getCurrency()).isEqualTo(Currency.getInstance("EUR"));
    }

    @Test
    public void getCurrencyReturnPLNCurrency() {
        assertThat(Money.Type.PLN.getCurrency()).isEqualTo(Currency.getInstance("PLN"));
    }

    @Test
    public void getCurrencyReturnUAHCurrency() {
        assertThat(Money.Type.UAH.getCurrency()).isEqualTo(Currency.getInstance("UAH"));
    }

    @Test
    public void getCurrencyReturnUSDCurrency() {
        assertThat(Money.Type.USD.getCurrency()).isEqualTo(Currency.getInstance("USD"));
    }

    @Test
    public void getCurrencyReturnRUBCurrency() {
        assertThat(Money.Type.RUB.getCurrency()).isEqualTo(Currency.getInstance("RUB"));
    }

    @Test
    public void moneyTypeOfStringReturnUSD() {
        assertThat(Money.Type.of("USD")).isEqualTo(Money.Type.USD);
    }

    @Test
    public void moneyTypeOfStringReturnEUR() {
        assertThat(Money.Type.of("EUR")).isEqualTo(Money.Type.EUR);
    }

    @Test
    public void moneyTypeOfStringReturnPLN() {
        assertThat(Money.Type.of("PLN")).isEqualTo(Money.Type.PLN);
    }

    @Test
    public void moneyTypeOfStringReturnUAH() {
        assertThat(Money.Type.of("UAH")).isEqualTo(Money.Type.UAH);
    }

    @Test
    public void moneyTypeOfStringReturnRUB() {
        assertThat(Money.Type.of("RUB")).isEqualTo(Money.Type.RUB);
    }

    @Test
    public void moneyTypeOfStringIgnoreExtraWhiteSymbol() {
        assertThat(Money.Type.of(" USD ")).isEqualTo(Money.Type.USD);
        assertThat(Money.Type.of("      USD    ")).isEqualTo(Money.Type.USD);
    }

    @Test
    public void moneyTypeOfStringIgnoreCase() {
        assertThat(Money.Type.of("uSd")).isEqualTo(Money.Type.USD);
        assertThat(Money.Type.of("USd")).isEqualTo(Money.Type.USD);
        assertThat(Money.Type.of("usD")).isEqualTo(Money.Type.USD);
        assertThat(Money.Type.of("usd")).isEqualTo(Money.Type.USD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyTypeOfStringWithWrongCodeThenIAE() {
        Money.Type.of("UsDd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyTypeOfStringWithWrongArgumentThenIAE() {
        Money.Type.of(" USD EUR ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMoneyTypeOfStringArgumentNullThenIAE() {
        Money.Type.of(null);
    }

}