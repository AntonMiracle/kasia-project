package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.model.Balance;
import com.kasia.model.Currencies;
import com.kasia.model.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceServiceIT {
    @Autowired
    private BalanceService bService;

    @Test
    public void createBalance() {
        Balance expected = ModelTestData.getBalance1();

        Balance actual = bService.createBalance(expected.getAmount(), expected.getCurrencies());

        assertThat(actual.getChangeOn().compareTo(LocalDateTime.now().plusSeconds(2)) < 0).isTrue();
        assertThat(actual.getAmount()).isEqualTo(expected.getAmount());
        assertThat(actual.getCurrencies()).isEqualTo(expected.getCurrencies());
    }

    @Test
    public void createPrice() {
        Price expected = ModelTestData.getPrice1();

        Price actual = bService.createPrice(expected.getAmount(), expected.getCurrencies());

        assertThat(actual.getAmount()).isEqualTo(expected.getAmount());
        assertThat(actual.getCurrencies()).isEqualTo(expected.getCurrencies());
    }

    @Test
    public void add() throws Exception {
        Currencies currencies = Currencies.EUR;
        Price price = ModelTestData.getPrice1();
        price.setAmount(BigDecimal.valueOf(1.0001));
        price.setCurrencies(currencies);
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCurrencies(currencies);

        assertThat(bService.add(balance, price).getAmount()).isEqualTo(BigDecimal.valueOf(1.0001));
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(1.0001));
    }

    @Test
    public void addManyPrices() throws Exception {
        Currencies currencies = Currencies.EUR;
        Price price = ModelTestData.getPrice1();
        price.setAmount(BigDecimal.valueOf(1.001));
        price.setCurrencies(currencies);
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCurrencies(currencies);

        assertThat(bService.add(balance, price, price, price, price, price).getAmount()).isEqualTo(BigDecimal.valueOf(5.005));
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(5.005));
    }

    @Test
    public void addZeroPrice() throws Exception {
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);

        assertThat(bService.add(balance).getAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenAddWithDifferentCurrencyThenException() throws Exception {
        Price price = ModelTestData.getPrice1();
        price.setCurrencies(Currencies.USD);
        Balance balance = ModelTestData.getBalance1();
        balance.setCurrencies(Currencies.EUR);
        bService.add(balance, price);
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenSubtractWithDifferentCurrencyThenException() throws Exception {
        Price price = ModelTestData.getPrice1();
        price.setCurrencies(Currencies.USD);
        Balance balance = ModelTestData.getBalance1();
        balance.setCurrencies(Currencies.EUR);
        bService.subtract(balance, price);
    }

    @Test
    public void subtract() throws Exception {
        Currencies currencies = Currencies.EUR;
        Price price = ModelTestData.getPrice1();
        price.setAmount(BigDecimal.valueOf(1.0001));
        price.setCurrencies(currencies);
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCurrencies(currencies);

        assertThat(bService.subtract(balance, price).getAmount()).isEqualTo(BigDecimal.valueOf(-1.0001));
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(-1.0001));
    }

    @Test
    public void subtractManyPrices() throws Exception {
        Currencies currencies = Currencies.EUR;
        Price price = ModelTestData.getPrice1();
        price.setAmount(BigDecimal.valueOf(1.0001));
        price.setCurrencies(currencies);
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCurrencies(currencies);

        assertThat(bService.subtract(balance, price, price, price, price, price).getAmount()).isEqualTo(BigDecimal.valueOf(-5.0005));
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(-5.0005));
    }

    @Test
    public void subtractZeroPrices() throws Exception {
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);

        assertThat(bService.subtract(balance).getAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void parseToString() throws Exception {
        Balance balance1 = ModelTestData.getBalance1();
        balance1.setAmount(BigDecimal.valueOf(-110.005).setScale(2, BigDecimal.ROUND_HALF_UP));
        Locale locale1 = new Locale("en", "GB");
        Locale locale2 = new Locale("sl", "SI");
        Locale locale3 = new Locale("en", "CA");
        Locale locale4 = new Locale("nl", "BE");

        assertThat(bService.parseToString(balance1, locale1)).isEqualTo("-€110.01");
        assertThat(bService.parseToString(balance1, locale2)).isEqualTo("-€ 110,01");
        assertThat(bService.parseToString(balance1, locale3)).isEqualTo("-EUR110.01");
        assertThat(bService.parseToString(balance1, locale4)).isEqualTo("-110,01 €");
    }

    @Test
    public void createValue() {
        BigInteger banknotes1 = new BigInteger("-100");
        BigInteger banknotes2 = new BigInteger("-0");
        BigInteger banknotes3 = new BigInteger("0");
        BigInteger banknotes4 = new BigInteger("100");
        BigInteger penny1 = new BigInteger("99");
        BigInteger penny2 = new BigInteger("00");

        assertThat(bService.createValue(banknotes1,penny1)).isEqualTo(new BigDecimal("-100.99"));
        assertThat(bService.createValue(banknotes1,penny2)).isEqualTo(new BigDecimal("-100.0"));
        assertThat(bService.createValue(banknotes2,penny1)).isEqualTo(new BigDecimal("0.99"));
        assertThat(bService.createValue(banknotes2,penny2)).isEqualTo(new BigDecimal("0.0"));
        assertThat(bService.createValue(banknotes3,penny1)).isEqualTo(new BigDecimal("0.99"));
        assertThat(bService.createValue(banknotes3,penny2)).isEqualTo(new BigDecimal("0.0"));
        assertThat(bService.createValue(banknotes4,penny1)).isEqualTo(new BigDecimal("100.99"));
        assertThat(bService.createValue(banknotes4,penny2)).isEqualTo(new BigDecimal("100.0"));
    }
}