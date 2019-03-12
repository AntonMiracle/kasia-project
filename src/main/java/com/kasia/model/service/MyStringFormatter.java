package com.kasia.model.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MyStringFormatter {
    String formatNumberByLocale(boolean makeNegative, long userId, BigDecimal amount);

    String formatNumberByLocale(long userId, BigDecimal amount);

    String formatDateByLocale(long userId, LocalDateTime date);
}
