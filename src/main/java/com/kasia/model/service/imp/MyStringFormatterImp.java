package com.kasia.model.service.imp;

import com.kasia.model.User;
import com.kasia.model.service.MyStringFormatter;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MyStringFormatterImp implements MyStringFormatter {
    @Autowired
    private UserService uService;

    @Override
    public String formatNumberByLocale(boolean makeNegative, long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0 && makeNegative)
            return formatNumberByLocale(userId, new BigDecimal("-" + amount.toString()));
        else
            return formatNumberByLocale(userId, amount);
    }

    @Override
    public String formatNumberByLocale(long userId, BigDecimal amount) {
        User user = uService.findUserById(userId);
        NumberFormat formatter = NumberFormat.getInstance(user.getLocale());
        formatter.setMaximumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return formatter.format(amount);
    }

    @Override
    public String formatDateByLocale(long userId, LocalDateTime date) {
        User user = uService.findUserById(userId);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd EE HH:mm", user.getLocale());
        return date.format(f);
    }
}
