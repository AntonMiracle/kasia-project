package com.kasia.model.validation;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetProviderValidationIT {
    @Autowired
    private BudgetProviderValidation validationService;

    @Test
    public void elementProviderIsValid() {
        BudgetProvider budgetProvider1 = ModelTestData.getBudgetElementProvider1();
        BudgetProvider budgetProvider2 = ModelTestData.getBudgetElementProvider2();

        assertThat(validationService.isValid(budgetProvider1)).isTrue();
        assertThat(validationService.isValid(budgetProvider2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        BudgetProvider budgetProvider1 = ModelTestData.getBudgetElementProvider1();
        budgetProvider1.setBudget(null);
        BudgetProvider budgetProvider2 = ModelTestData.getBudgetElementProvider1();
        budgetProvider2.setProviders(null);

        assertThat(validationService.isValid(budgetProvider1)).isFalse();
        assertThat(validationService.isValid(budgetProvider2)).isFalse();
    }
}