package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.UserConnectBudget;
import com.kasia.model.validation.UserConnectBudgetValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConnectBudgetValidationServiceIT {

    @Autowired
    private UserConnectBudgetValidationService validationService;

    @Test
    public void elementProviderIsValid() {
        UserConnectBudget userConnectBudget1 = ModelTestData.getUserConnectBudget1();
        UserConnectBudget userConnectBudget2 = ModelTestData.getUserConnectBudget2();

        assertThat(validationService.isValid(userConnectBudget1)).isTrue();
        assertThat(validationService.isValid(userConnectBudget2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        UserConnectBudget userConnectBudget1 = ModelTestData.getUserConnectBudget1();
        userConnectBudget1.setUser(null);
        UserConnectBudget userConnectBudget2 = ModelTestData.getUserConnectBudget1();
        userConnectBudget2.setConnectBudgets(null);

        assertThat(validationService.isValid(userConnectBudget1)).isFalse();
        assertThat(validationService.isValid(userConnectBudget2)).isFalse();
    }


}