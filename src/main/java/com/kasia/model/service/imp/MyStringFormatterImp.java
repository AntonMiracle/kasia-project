package com.kasia.model.service.imp;

import com.kasia.model.User;
import com.kasia.model.service.MyStringFormatter;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

@Service
public class MyStringFormatterImp implements MyStringFormatter {
    @Autowired
    private UserService uService;

    @Override
    public String formatByLocale(boolean makeNegative, long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0 && makeNegative)
            return formatByLocale(userId, new BigDecimal("-" + amount.toString()));
        else
            return formatByLocale(userId, amount);
    }

    @Override
    public String formatByLocale(long userId, BigDecimal amount) {
        User user = uService.findUserById(userId);
        NumberFormat formatter = NumberFormat.getInstance(user.getLocale());
        formatter.setMaximumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return formatter.format(amount);
    }
}
