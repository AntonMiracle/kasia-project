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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyStringFormatterTest {
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
    public void formatByLocalePositive() {
        BigDecimal number = new BigDecimal("1000000.10");
        assertThat(formatter.formatByLocale(user.getId(),number)).isEqualTo("1,000,000.1");
        number = new BigDecimal("-"+number.toString());
        assertThat(formatter.formatByLocale(user.getId(),number)).isEqualTo("-1,000,000.1");
        number = new BigDecimal("0.001");
        assertThat(formatter.formatByLocale(user.getId(),number)).isEqualTo("0");
        number = new BigDecimal("0.005");
        assertThat(formatter.formatByLocale(user.getId(),number)).isEqualTo("0.01");
        number = new BigDecimal("0.01");
        assertThat(formatter.formatByLocale(user.getId(),number)).isEqualTo("0.01");
        number = new BigDecimal("2.4");
        assertThat(formatter.formatByLocale(user.getId(),number)).isEqualTo("2.4");
    }
    @Test
    public void formatByLocaleNegative() {
        BigDecimal number = new BigDecimal("1000000.10");
        assertThat(formatter.formatByLocale(true,user.getId(),number)).isEqualTo("-1,000,000.1");
        number = new BigDecimal("0.001");
        assertThat(formatter.formatByLocale(true,user.getId(),number)).isEqualTo("-0");
        number = new BigDecimal("0.005");
        assertThat(formatter.formatByLocale(true,user.getId(),number)).isEqualTo("-0.01");
        number = new BigDecimal("0.01");
        assertThat(formatter.formatByLocale(true,user.getId(),number)).isEqualTo("-0.01");
        number = new BigDecimal("2.4");
        assertThat(formatter.formatByLocale(true,user.getId(),number)).isEqualTo("-2.4");
    }
}