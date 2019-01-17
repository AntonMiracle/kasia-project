package com.kasia.model.validation;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetElementProvider;
import com.kasia.model.validation.BudgetElementProviderValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetElementProviderValidationServiceIT {
    @Autowired
    private BudgetElementProviderValidationService validationService;

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