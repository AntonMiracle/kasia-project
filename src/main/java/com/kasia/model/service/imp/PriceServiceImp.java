package com.kasia.model.service.imp;

import com.kasia.exception.NoCurrenciesRuntimeException;
import com.kasia.exception.PriceNegativeRuntimeException;
import com.kasia.model.Currencies;
import com.kasia.model.Price;
import com.kasia.model.service.PriceService;
import com.kasia.model.validation.PriceValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImp implements PriceService {
    @Autowired
    private PriceValidationService priceValidationService;

    @Override
    public Price create(BigDecimal amount, Currencies currencies) {
        if (amount.doubleValue() < 0) throw new PriceNegativeRuntimeException();
        if (currencies == null) throw new NoCurrenciesRuntimeException();
        Price price = new Price(amount, currencies);
        priceValidationService.verifyValidation(price);
        return price;
    }
}
