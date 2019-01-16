package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceValidationServiceIT {
    @Autowired
    private ValidationService<Price> validationService;


    @Test
    public void isValid() {
        Price price1 = ModelTestData.getPrice1();
        Price price2 = ModelTestData.getPrice2();

        assertThat(validationService.isValid(price2)).isTrue();
        assertThat(validationService.isValid(price1)).isTrue();
    }

    @Test
    public void invalidWithNull() {
        Price price1 = ModelTestData.getPrice1();
        price1.setAmount(null);
        Price price2 = ModelTestData.getPrice1();
        price2.setCurrencies(null);

        assertThat(validationService.isValid(price1)).isFalse();
        assertThat(validationService.isValid(price2)).isFalse();
    }

    @Test
    public void invalidWithNonCorrectField() {
        Price price1 = ModelTestData.getPrice1();
        price1.setAmount(BigDecimal.valueOf(-0.01));

        assertThat(validationService.isValid(price1)).isFalse();
    }

}