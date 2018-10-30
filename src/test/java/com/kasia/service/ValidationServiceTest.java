package com.kasia.service;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.model.User;
import com.kasia.ConfigurationEjbCdiContainerForIT;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidationServiceTest extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ValidationService<User> userValidationService;
    @Inject
    private ValidationService<Article> articleValidationService;
    @Inject
    private ValidationService<Budget> budgetValidationService;
    @Inject
    private ValidationService<Economy> economyValidationService;

    @Test
    public void articleIsValidTrue() {
        Article article = new Article(Article.Type.CONSUMPTION, BigDecimal.ONE, LocalDateTime.now());
        assertThat(articleValidationService.isValid(article)).isTrue();
    }

    @Test
    public void budgetIsValidTrue() {
        Budget budget = new Budget("Name", BigDecimal.ZERO, Currency.getInstance("EUR"), LocalDateTime.now());
        assertThat(budgetValidationService.isValid(budget)).isTrue();
    }

    @Test
    public void economyIsValidTrue() {
        Economy economy = new Economy("name", LocalDateTime.now());
        assertThat(economyValidationService.isValid(economy)).isTrue();
    }

    @Test
    public void userIsValidTrue() {
        User user = new User(User.Role.USER, "email", "nick", "password", ZoneId.systemDefault(), LocalDateTime.now());
        assertThat(userValidationService.isValid(user)).isTrue();
    }
}