package com.kasia.validation;

import com.kasia.ConfigurationEjbCdiContainerForIT;
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
    private ValidationService validationService;


    @Test
    public void articleIsValidTrue() {
        Article article = new Article("Name_2-.", "", Article.Type.CONSUMPTION);

        assertThat(validationService).isNotNull();
        assertThat(validationService.isValid(article)).isTrue();
    }

    @Test
    public void budgetIsValidTrue() {
        Budget budget = new Budget("Name_2-.", new HashSet<>(), BigDecimal.ZERO, LocalDateTime.now(), Currency.getInstance("EUR"));

        assertThat(validationService).isNotNull();
        assertThat(validationService.isValid(budget)).isTrue();
    }

    @Test
    public void userIsValidTrue() {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);

        User user = new User("email@gmail.com", "Password2", "Name_2-.", roles
                , new HashSet<>(), new HashSet<>(), new HashSet<>()
                , LocalDateTime.now().withNano(0), ZoneId.systemDefault());

        assertThat(validationService).isNotNull();
        assertThat(validationService.isValid(user)).isTrue();
    }

    @Test
    public void employerIsValidTrue() {
        Employer employer = new Employer("Name_2-.", "");

        assertThat(validationService).isNotNull();
        assertThat(validationService.isValid(employer)).isTrue();

    }

    @Test
    public void operationIsValidTrue() {
        Operation operation = new Operation(BigDecimal.TEN, new Article(), 2, new Employer(), LocalDateTime.now().withNano(0));

        assertThat(validationService).isNotNull();
        assertThat(validationService.isValid(operation)).isTrue();
    }
}