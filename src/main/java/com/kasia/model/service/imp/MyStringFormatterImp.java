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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MyStringFormatterImp implements MyStringFormatter {
    @Autowired
    private UserService userS;

    @Override
    public String formatNumber(boolean makeNegative, long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0 && makeNegative)
            return formatNumber(userId, new BigDecimal("-" + amount.toString()));
        else
            return formatNumber(userId, amount);
    }

    @Override
    public String formatNumber(long userId, BigDecimal amount) {
        User user = userS.findById(userId);
        NumberFormat formatter = NumberFormat.getInstance(user.getLocale());
        formatter.setMaximumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return formatter.format(amount);
    }

    @Override
    public String formatFullDate(long userId, LocalDateTime date) {
        User user = userS.findById(userId);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy EE HH:mm", user.getLocale());
        return convert(date, user.getZoneId()).format(f);
    }

    @Override
    public String formatOnlyDate(long userId, LocalDateTime date) {
        User user = userS.findById(userId);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy EE", user.getLocale());
        return convert(date, user.getZoneId()).format(f);
    }

    @Override
    public String formatOnlyTime(long userId, LocalDateTime date) {
        User user = userS.findById(userId);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm", user.getLocale());
        return convert(date, user.getZoneId()).format(f);
    }

    private ZonedDateTime convert(LocalDateTime date, ZoneId zoneId) {
        return date.atZone(ZoneId.systemDefault()).toInstant().atZone(zoneId);
    }
}
