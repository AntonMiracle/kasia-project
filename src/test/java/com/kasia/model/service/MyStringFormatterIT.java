package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyStringFormatterIT {
    @Autowired
    private UserService uService;
    @Autowired
    private MyStringFormatter formatter;
    private User user;

    @Before
    public void before() {
        user = ModelTestData.getUser1();
        uService.saveUser(user);
    }

    @After
    public void after() {
        uService.findAllUsers().forEach(u -> uService.deleteUser(u.getId()));
    }

    @Test
    public void formatNumberByLocalePositive() {
        BigDecimal number = new BigDecimal("1000000.10");
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("1,000,000.1");
        number = new BigDecimal("-" + number.toString());
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("-1,000,000.1");
        number = new BigDecimal("0.001");
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("0");
        number = new BigDecimal("0.005");
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("0.01");
        number = new BigDecimal("0.01");
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("0.01");
        number = new BigDecimal("2.4");
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("2.4");
        number = new BigDecimal("-2.4");
        assertThat(formatter.formatNumber(user.getId(), number)).isEqualTo("-2.4");
    }

    @Test
    public void formatNumberByLocaleNegative() {
        BigDecimal number = new BigDecimal("1000000.10");
        assertThat(formatter.formatNumber(true, user.getId(), number)).isEqualTo("-1,000,000.1");
        number = new BigDecimal("0.001");
        assertThat(formatter.formatNumber(true, user.getId(), number)).isEqualTo("-0");
        number = new BigDecimal("0.005");
        assertThat(formatter.formatNumber(true, user.getId(), number)).isEqualTo("-0.01");
        number = new BigDecimal("0.01");
        assertThat(formatter.formatNumber(true, user.getId(), number)).isEqualTo("-0.01");
        number = new BigDecimal("2.4");
        assertThat(formatter.formatNumber(true, user.getId(), number)).isEqualTo("-2.4");
        number = new BigDecimal("-2.4");
        assertThat(formatter.formatNumber(true, user.getId(), number)).isEqualTo("-2.4");
    }

    @Test
    public void formatDateByLocale() {
        user.setCreateOn(LocalDateTime.of(2019,03,12,22,03));
        assertThat(formatter.formatFullDate(user.getId(),user.getCreateOn())).isEqualTo("12-03-2019 Tue 22:03");
    }
}