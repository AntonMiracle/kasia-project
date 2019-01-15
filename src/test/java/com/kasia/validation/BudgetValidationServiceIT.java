package com.kasia.validation;

import com.kasia.ConfigurationSpringForTest;
import com.kasia.ModelTestData;
import com.kasia.model.Budget;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetValidationServiceIT extends ConfigurationSpringForTest {
    @Autowired
    private ValidationService<Budget> validationService;

    @Test
    public void budgetIsValid() {
        Budget budget1 = ModelTestData.getBudget1();
        Budget budget2 = ModelTestData.getBudget2();
        assertThat(validationService.isValid(budget1)).isTrue();
        assertThat(validationService.isValid(budget2)).isTrue();
    }

    @Test
    public void budgetInvalidWithNull() {
        Budget budget1 = ModelTestData.getBudget1();
        budget1.setName(null);
        Budget budget2 = ModelTestData.getBudget1();
        budget2.setCreateOn(null);
        Budget budget3 = ModelTestData.getBudget1();
        budget3.setBalance(null);

        assertThat(validationService.isValid(budget1)).isFalse();
        assertThat(validationService.isValid(budget2)).isFalse();
        assertThat(validationService.isValid(budget3)).isFalse();
    }

    @Test
    public void budgetInvalidWithNonCorrectField() {
        Budget budget1 = ModelTestData.getBudget1();
        budget1.setName("n");
        Budget budget2 = ModelTestData.getBudget1();
        budget2.setName(" name ");
        Budget budget3 = ModelTestData.getBudget1();
        budget3.setName("         ");
        Budget budget4 = ModelTestData.getBudget1();
        budget4.setCreateOn(ModelTestData.getNow().plusDays(23));

        assertThat(validationService.isValid(budget1)).isFalse();
        assertThat(validationService.isValid(budget2)).isFalse();
    }
}
