package com.kasia.model.service;

import com.kasia.model.Balance;
import com.kasia.model.Currencies;
import com.kasia.model.Price;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

public interface BalanceService {
    Balance add(Balance balanceTo, Price price);

    Balance add(Balance balanceTo, Price... prices);

    Balance subtract(Balance balanceFrom, Price price);

    Balance subtract(Balance balanceFrom, Price... prices);

    String parseToString(Balance balance, Locale locale);

    Price createPrice(BigDecimal amount, Currencies currencies);

    Balance createBalance(BigDecimal amount, Currencies currencies);

    BigDecimal createValue(BigInteger banknotes, BigInteger penny);
}
