package com.kasia.model.service;

import java.math.BigDecimal;

public interface MyStringFormatter {
    String formatByLocale(boolean makeNegative, long userId, BigDecimal amount);

    String formatByLocale(long userId, BigDecimal amount);
}
