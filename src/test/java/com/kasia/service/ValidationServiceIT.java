package com.kasia.service;

import com.kasia.model.*;
import com.kasia.ConfigurationEjbCdiContainerForIT;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidationServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ValidationService<User> userValidationService;
    @Inject
    private ValidationService<Article> articleValidationService;
    @Inject
    private ValidationService<Budget> budgetValidationService;
    @Inject
    private ValidationService<Employer> employerValidationService;
    @Inject
    private ValidationService<Operation> operationValidationService;

    @Test
    public void articleIsValidTrue() {
        Article article = new Article("Name", Article.Type.CONSUMPTION);
        assertThat(articleValidationService.isValid(article)).isTrue();
    }

    @Test
    public void budgetIsValidTrue() {
        Budget budget = new Budget("Name", BigDecimal.ZERO, Currency.getInstance("EUR"), LocalDateTime.now());
        assertThat(budgetValidationService.isValid(budget)).isTrue();
    }

    @Test
    public void userIsValidTrue() {
        User user = new User(User.Role.USER, "email", "nick", "password", ZoneId.systemDefault(), LocalDateTime.now());
        assertThat(userValidationService.isValid(user)).isTrue();
    }

    @Test
    public void employerIsValidTrue() {
        Employer employer = new Employer("Name");
        assertThat(employerValidationService.isValid(employer)).isTrue();

    }

    @Test
    public void operationIsValidTrue() {
        Operation operation = new Operation(BigDecimal.TEN, new Article(), new User(), new Employer(), LocalDateTime.now());
        assertThat(operationValidationService.isValid(operation)).isTrue();
    }
}