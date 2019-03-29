package com.kasia.model.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MyStringFormatter {
    String formatNumber(boolean makeNegative, long userId, BigDecimal amount);

    String formatNumber(long userId, BigDecimal amount);

    String formatFullDate(long userId, LocalDateTime date);

    String formatDate(long userId, LocalDateTime date);

    String formatOnlyDate(long userId, LocalDateTime date);

    String formatOnlyTime(long userId, LocalDateTime date);
}
