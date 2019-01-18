package com.kasia.model.validation;

import com.kasia.ModelTestData;
import com.kasia.model.Balance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceValidationIT {

    @Autowired
    private BalanceValidation validationService;


    @Test
    public void elementProviderIsValid() {
        Balance balance1 = ModelTestData.getBalance1();
        Balance balance2 = ModelTestData.getBalance2();

        assertThat(validationService.isValid(balance1)).isTrue();
        assertThat(validationService.isValid(balance2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        Balance balance1 = ModelTestData.getBalance1();
        balance1.setAmount(null);
        Balance balance2 = ModelTestData.getBalance1();
        balance2.setChangeOn(null);
        Balance balance3 = ModelTestData.getBalance1();
        balance3.setCurrencies(null);

        assertThat(validationService.isValid(balance1)).isFalse();
        assertThat(validationService.isValid(balance2)).isFalse();
        assertThat(validationService.isValid(balance3)).isFalse();
    }

    @Test
    public void elementProviderInvalidWithNonCorrectField() {
        Balance balance1 = ModelTestData.getBalance1();
        balance1.setChangeOn(ModelTestData.getNow().plusDays(2));

        assertThat(validationService.isValid(balance1)).isFalse();
    }

}