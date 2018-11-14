package com.kasia.service.validation;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.*;
import com.kasia.service.validation.field.*;
import com.kasia.service.validation.message.*;
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
    private ValidationService<User,UField,UMessageLink> userValidationService;
    @Inject
    private ValidationService<Article, AField, AMessageLink> articleValidationService;
    @Inject
    private ValidationService<Budget,BField,BMessageLink> budgetValidationService;
    @Inject
    private ValidationService<Employer,EField,EMessageLink> employerValidationService;
    @Inject
    private ValidationService<Operation,OField,OMessageLink> operationValidationService;

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
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);

        User user = new User("email@gmail.com", "Password2",  "Nick", roles
                , new HashSet<>(), new HashSet<>(), new HashSet<>()
                , LocalDateTime.now().withNano(0), ZoneId.systemDefault());

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