package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.UserBudget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBudgetValidationServiceIT {

    @Autowired
    private ValidationService<UserBudget> validationService;

    @Test
    public void elementProviderIsValid() {
        UserBudget userBudget1 = ModelTestData.getUserBudget1();
        UserBudget userBudget2 = ModelTestData.getUserBudget2();

        assertThat(validationService.isValid(userBudget1)).isTrue();
        assertThat(validationService.isValid(userBudget2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        UserBudget userBudget1 = ModelTestData.getUserBudget1();
        userBudget1.setUser(null);
        UserBudget userBudget2 = ModelTestData.getUserBudget2();
        userBudget2.setBudgets(null);

        assertThat(validationService.isValid(userBudget1)).isFalse();
        assertThat(validationService.isValid(userBudget2)).isFalse();
    }

}