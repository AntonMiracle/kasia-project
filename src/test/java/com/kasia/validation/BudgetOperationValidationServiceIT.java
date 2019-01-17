package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetOperation;
import com.kasia.model.validation.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetOperationValidationServiceIT {

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