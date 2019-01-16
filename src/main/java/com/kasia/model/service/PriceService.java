package com.kasia.model.service;

import com.kasia.model.Currencies;
import com.kasia.model.Price;

import java.math.BigDecimal;

public interface PriceService {
    Price create(BigDecimal amount, Currencies currencies);
}
