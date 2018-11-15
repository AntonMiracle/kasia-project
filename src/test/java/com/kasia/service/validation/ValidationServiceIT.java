package com.kasia.service.validation;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.exception.RegexNotExistRunTimeException;
import com.kasia.model.*;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

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

    @Test(expected = RegexNotExistRunTimeException.class)
    public void whenMatchOnNotExistingRegexThenRegexNotExistRunTimeException() {
        articleValidationService.isMatches("", "3wd");
    }

    @Test
    public void articleIsValidTrue() {
        Article article = new Article("Name_2-.", "", Article.Type.CONSUMPTION);

        assertThat(articleValidationService).isNotNull();
        assertThat(articleValidationService.isValid(article)).isTrue();
    }

    @Test
    public void budgetIsValidTrue() {
        Budget budget = new Budget("Name_2-.", new HashSet<>(), BigDecimal.ZERO, LocalDateTime.now(), Currency.getInstance("EUR"));

        assertThat(budgetValidationService).isNotNull();
        assertThat(budgetValidationService.isValid(budget)).isTrue();
    }

    @Test
    public void userIsValidTrue() {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);

        User user = new User("email@gmail.com", "Password2", "Name_2-.", roles
                , new HashSet<>(), new HashSet<>(), new HashSet<>()
                , LocalDateTime.now().withNano(0), ZoneId.systemDefault());

        assertThat(userValidationService).isNotNull();
        assertThat(userValidationService.isValid(user)).isTrue();
    }

    @Test
    public void employerIsValidTrue() {
        Employer employer = new Employer("Name_2-.", "");

        assertThat(employerValidationService).isNotNull();
        assertThat(employerValidationService.isValid(employer)).isTrue();

    }

    @Test
    public void operationIsValidTrue() {
        Operation operation = new Operation(BigDecimal.TEN, new Article(), new User(), new Employer(), LocalDateTime.now());

        assertThat(operationValidationService).isNotNull();
        assertThat(operationValidationService.isValid(operation)).isTrue();
    }
}