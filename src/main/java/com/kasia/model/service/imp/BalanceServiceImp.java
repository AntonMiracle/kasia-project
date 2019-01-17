package com.kasia.model.service.imp;

import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.model.Balance;
import com.kasia.model.Price;
import com.kasia.model.service.BalanceService;
import com.kasia.model.validation.BalanceValidationService;
import com.kasia.model.validation.PriceValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class BalanceServiceImp implements BalanceService {
    @Autowired
    private PriceValidationService priceValidationService;
    @Autowired
    private BalanceValidationService balanceValidationService;

    @Override
    public Balance add(Balance balanceTo, Price price) {
        balanceValidationService.verifyValidation(balanceTo);
        priceValidationService.verifyValidation(price);
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
        balanceValidationService.verifyValidation(balanceTo);
        priceValidationService.verifyValidation(price);
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        if (balanceTo.getCurrencies() != price.getCurrencies()) throw new CurrenciesNotEqualsRuntimeException();
        BigDecimal newAmount = balanceTo.getAmount().subtract(price.getAmount());
        balanceTo.setAmount(newAmount);
        return balanceTo;
    }

    @Override
    public String parseToString(Balance balance, Locale locale) {
        balanceValidationService.verifyValidation(balance);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(balance.getCurrencies().getCurrency());
        return currencyFormat.format(balance.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
