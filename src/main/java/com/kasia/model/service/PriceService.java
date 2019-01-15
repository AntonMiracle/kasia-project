package com.kasia.model.service;

import com.kasia.model.Price;

public interface PriceService {
    Price add(Price... prices);

    Price substruct(Price from, Price... prices);
}
