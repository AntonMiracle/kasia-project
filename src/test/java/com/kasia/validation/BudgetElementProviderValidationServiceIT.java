package com.kasia.validation;

import com.kasia.ConfigurationSpringForTest;
import com.kasia.ModelTestData;
import com.kasia.model.BudgetElementProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetElementProviderValidationServiceIT extends ConfigurationSpringForTest {
    @Autowired
    private ValidationService<BudgetElementProvider> validationService;

    @Test
    public void elementProviderIsValid() {
        BudgetElementProvider budgetElementProvider1 = ModelTestData.getBudgetElementProvider1();
        BudgetElementProvider budgetElementProvider2 = ModelTestData.getBudgetElementProvider2();

        assertThat(validationService.isValid(budgetElementProvider1)).isTrue();
        assertThat(validationService.isValid(budgetElementProvider2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        BudgetElementProvider budgetElementProvider1 = ModelTestData.getBudgetElementProvider1();
        budgetElementProvider1.setBudget(null);
        BudgetElementProvider budgetElementProvider2 = ModelTestData.getBudgetElementProvider1();
        budgetElementProvider2.setElementProviders(null);

        assertThat(validationService.isValid(budgetElementProvider1)).isFalse();
        assertThat(validationService.isValid(budgetElementProvider2)).isFalse();
    }
}