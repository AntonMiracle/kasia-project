package com.kasia.model.service.imp;

import com.kasia.model.Price;
import com.kasia.model.service.PriceService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImp implements PriceService, ValidationService<Price> {
    @Override
    public Price add(Price... prices) {
        return null;
    }

    @Override
    public Price substruct(Price from, Price... prices) {
        return null;
    }
}
