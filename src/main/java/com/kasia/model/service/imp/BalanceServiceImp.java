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
        if (balanceTo.getCurrency() != price.getCurrency()) throw new CurrenciesNotEqualsRuntimeException();
        BigDecimal newAmount = balanceTo.getAmount().add(price.getAmount());
        balanceTo.setAmount(newAmount);
        return balanceTo;
    }

    @Override
    public Balance subtract(Balance balanceTo, Price price) {
        if (balanceTo.getCurrency() != price.getCurrency()) throw new CurrenciesNotEqualsRuntimeException();
        if (balanceTo.getCurrency() != price.getCurrency()) throw new CurrenciesNotEqualsRuntimeException();
        BigDecimal newAmount = balanceTo.getAmount().subtract(price.getAmount());
        balanceTo.setAmount(newAmount);
        return balanceTo;
    }

    @Override
    public String parseToString(Balance balance, Locale locale) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(balance.getCurrency().getCurrency());
        return currencyFormat.format(balance.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
