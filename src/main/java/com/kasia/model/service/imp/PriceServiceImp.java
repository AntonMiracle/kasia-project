package com.kasia.model.service.imp;

import com.kasia.model.Currencies;
import com.kasia.model.Price;
import com.kasia.model.service.PriceService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImp implements PriceService, ValidationService<Price> {

    @Override
    public Price create(BigDecimal amount, Currencies currencies) {
        return null;
    }
}
