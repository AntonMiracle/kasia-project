package com.kasia.model.service;

import com.kasia.model.Balance;
import com.kasia.model.Price;

import java.util.Locale;

public interface BalanceService {
    Balance add(Balance balanceTo, Price price);

    Balance add(Balance balanceTo, Price... prices);

    Balance subtract(Balance balanceTo, Price price);

    Balance subtract(Balance balanceFrom, Price... prices);

    String parseToString(Balance balance, Locale locale);


}
