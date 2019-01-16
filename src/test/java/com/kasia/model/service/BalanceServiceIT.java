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
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceServiceIT {
    @Autowired
    private BalanceService balanceService;

    @Test
    public void add() throws Exception {
        Currencies currencies = Currencies.EUR;
        Price price = ModelTestData.getPrice1();
        price.setAmount(BigDecimal.valueOf(1.0001));
        price.setCurrency(currencies);
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCurrency(currencies);

        assertThat(balanceService.add(balance, price).getAmount()).isEqualTo(BigDecimal.valueOf(1.0001));
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(1.0001));
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenAddWithDifferentCurrencyThenException() throws Exception {
        Price price = ModelTestData.getPrice1();
        price.setCurrency(Currencies.USD);
        Balance balance = ModelTestData.getBalance1();
        balance.setCurrency(Currencies.EUR);
        balanceService.add(balance, price);
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenSubtractWithDifferentCurrencyThenException() throws Exception {
        Price price = ModelTestData.getPrice1();
        price.setCurrency(Currencies.USD);
        Balance balance = ModelTestData.getBalance1();
        balance.setCurrency(Currencies.EUR);
        balanceService.subtract(balance, price);
    }

    @Test
    public void subtract() throws Exception {
        Currencies currencies = Currencies.EUR;
        Price price = ModelTestData.getPrice1();
        price.setAmount(BigDecimal.valueOf(1.0001));
        price.setCurrency(currencies);
        Balance balance = ModelTestData.getBalance1();
        balance.setAmount(BigDecimal.ZERO);
        balance.setCurrency(currencies);

        assertThat(balanceService.subtract(balance, price).getAmount()).isEqualTo(BigDecimal.valueOf(-1.0001));
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(-1.0001));
    }

    @Autowired
    private UserService userService;

    @Test
    public void parseToString() throws Exception {
        Balance balance1 = ModelTestData.getBalance1();
        balance1.setAmount(BigDecimal.valueOf(-110.005).setScale(2, BigDecimal.ROUND_HALF_UP));
        Locale locale1 = new Locale("en", "GB");
        Locale locale2 = new Locale("sl", "SI");
        Locale locale3 = new Locale("en", "CA");
        Locale locale4 = new Locale("nl", "BE");

        Set<Locale> locales = userService.getCorrectAvailableLocales();
        assertThat(locales.contains(locale1)).isTrue();
        assertThat(locales.contains(locale2)).isTrue();
        assertThat(locales.contains(locale3)).isTrue();
        assertThat(locales.contains(locale4)).isTrue();

        assertThat(balanceService.parseToString(balance1, locale1)).isEqualTo("-€110.01");
        assertThat(balanceService.parseToString(balance1, locale2)).isEqualTo("-€ 110,01");
        assertThat(balanceService.parseToString(balance1, locale3)).isEqualTo("-EUR110.01");
        assertThat(balanceService.parseToString(balance1, locale4)).isEqualTo("-110,01 €");
    }
}