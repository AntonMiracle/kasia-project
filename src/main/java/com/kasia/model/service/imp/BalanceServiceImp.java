package com.kasia.model.service.imp;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.NoCurrenciesRuntimeException;
import com.kasia.exception.PriceNegativeRuntimeException;
import com.kasia.model.Balance;
import com.kasia.model.Currencies;
import com.kasia.model.Price;
import com.kasia.model.service.BalanceService;
import com.kasia.model.validation.BalanceValidation;
import com.kasia.model.validation.PriceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class BalanceServiceImp implements BalanceService {
    @Autowired
    private PriceValidation pValidation;
    @Autowired
    private BalanceValidation bValidation;

    @Override
    public Price createPrice(BigDecimal amount, Currencies currencies) {
        if (amount.doubleValue() < 0) throw new PriceNegativeRuntimeException();
        if (currencies == null) throw new NoCurrenciesRuntimeException();
        Price price = new Price(amount, currencies);
        pValidation.verifyValidation(price);
        return price;
    }

    @Override
    public Balance createBalance(BigDecimal amount, Currencies currencies) {
        if (currencies == null) throw new NoCurrenciesRuntimeException();
        Balance balance = new Balance(amount, currencies, LocalDateTime.now().withNano(0));
        bValidation.verifyValidation(balance);
        return balance;
    }

    @Override
    public Balance createBalance(String banknotesValue, String pennyValue, String currenciesValue) {
        BigDecimal amount = createValue(new BigInteger(banknotesValue), new BigInteger(pennyValue));
        Currencies currencies = Currencies.valueOf(currenciesValue);
        return createBalance(amount, currencies);
    }

    @Override
    public BigDecimal createValue(BigInteger banknotesValue, BigInteger pennyValue) {
        double penny = pennyValue.intValue() / 100.0;
        if (banknotesValue.compareTo(BigInteger.ZERO) >= 0) {
            return new BigDecimal(banknotesValue.toString()).add(BigDecimal.valueOf(penny));
        } else {
            return new BigDecimal(banknotesValue.toString()).subtract(BigDecimal.valueOf(penny));
        }
    }

    @Override
    public Balance add(Balance balanceTo, Price price) {
        bValidation.verifyValidation(balanceTo);
        pValidation.verifyValidation(price);
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        BigDecimal newAmount = balanceTo.getAmount().add(price.getAmount());
        balanceTo.setAmount(newAmount);
        return balanceTo;
    }

    @Override
    public Balance add(Balance balanceTo, Price... prices) {
        Price sum = makeSum(prices);
        if (sum == null) return balanceTo;
        return add(balanceTo, sum);
    }

    @Override
    public Balance subtract(Balance balanceFrom, Price... prices) {
        Price sum = makeSum(prices);
        if (sum == null) return balanceFrom;
        return subtract(balanceFrom, sum);
    }

    private Price makeSum(Price... prices) {
        if (prices.length == 0) return null;

        Price sum = new Price();
        sum.setAmount(BigDecimal.ZERO);
        sum.setCurrencies(prices[0].getCurrencies());
        for (Price price : prices) {

            if (price.getCurrencies() == null || sum.getCurrencies() != price.getCurrencies())
                throw new CurrenciesNotEqualsRuntimeException();

            sum.setAmount(sum.getAmount().add(price.getAmount()));
        }
        return sum;
    }

    @Override
    public Balance subtract(Balance balanceTo, Price price) {
        bValidation.verifyValidation(balanceTo);
        pValidation.verifyValidation(price);
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        BigDecimal newAmount = balanceTo.getAmount().subtract(price.getAmount());
        balanceTo.setAmount(newAmount);
        return balanceTo;
    }

    @Override
    public String parseToString(Balance balance, Locale locale) {
        bValidation.verifyValidation(balance);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(balance.getCurrencies().getCurrency());
        return currencyFormat.format(balance.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}