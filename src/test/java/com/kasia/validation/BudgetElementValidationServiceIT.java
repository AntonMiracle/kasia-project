package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetElementValidationServiceIT {

    @Autowired
    private ValidationService<BudgetElement> validationService;

    @Test
    public void elementProviderIsValid() {
        BudgetElement budgetElementProvider1 = ModelTestData.getBudgetElement1();
        BudgetElement budgetElementProvider2 = ModelTestData.getBudgetElement2();

        assertThat(validationService.isValid(budgetElementProvider1)).isTrue();
        assertThat(validationService.isValid(budgetElementProvider2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        BudgetElement budgetElement1 = ModelTestData.getBudgetElement1();
        budgetElement1.setBudget(null);
        BudgetElement budgetElement2 = ModelTestData.getBudgetElement1();
        budgetElement2.setElements(null);

        assertThat(validationService.isValid(budgetElement1)).isFalse();
        assertThat(validationService.isValid(budgetElement2)).isFalse();
    }
}