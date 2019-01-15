package com.kasia.validation;

import com.kasia.ConfigurationSpringForTest;
import com.kasia.ModelTestData;
import com.kasia.model.BudgetOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetOperationValidationServiceIT extends ConfigurationSpringForTest {

    @Autowired
    private ValidationService<BudgetOperation> validationService;

    @Test
    public void elementProviderIsValid() {
        BudgetOperation budgetOperation1 = ModelTestData.getBudgetOperation1();
        BudgetOperation budgetOperation2 = ModelTestData.getBudgetOperation2();

        assertThat(validationService.isValid(budgetOperation1)).isTrue();
        assertThat(validationService.isValid(budgetOperation2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        BudgetOperation budgetOperation1 = ModelTestData.getBudgetOperation1();
        budgetOperation1.setBudget(null);
        BudgetOperation budgetOperation2 = ModelTestData.getBudgetOperation1();
        budgetOperation2.setOperations(null);

        assertThat(validationService.isValid(budgetOperation1)).isFalse();
        assertThat(validationService.isValid(budgetOperation2)).isFalse();
    }

}