package com.kasia.model.service.imp;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.model.Balance;
import com.kasia.model.Price;
import com.kasia.model.service.BalanceService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class BalanceServiceImp implements BalanceService, ValidationService<Balance> {

    @Override
    public Balance add(Balance balanceTo, Price price) {
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
        for (Price price : prices) {

            if (sum.getCurrencies() == null) sum.setCurrencies(price.getCurrencies());
            if (price.getCurrencies() == null || sum.getCurrencies() != price.getCurrencies())
                throw new CurrenciesNotEqualsRuntimeException();

            sum.setAmount(sum.getAmount().add(price.getAmount()));
        }
        return sum;
    }

    @Override
    public Balance subtract(Balance balanceTo, Price price) {
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        BigDecimal newAmount = balanceTo.getAmount().subtract(price.getAmount());
        balanceTo.setAmount(newAmount);
        return balanceTo;
    }

    @Override
    public String parseToString(Balance balance, Locale locale) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(balance.getCurrencies().getCurrency());
        return currencyFormat.format(balance.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
