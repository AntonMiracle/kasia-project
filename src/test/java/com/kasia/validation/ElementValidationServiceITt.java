package com.kasia.validation;

import com.kasia.ModelTestData;
import com.kasia.model.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElementValidationServiceITt {
    @Autowired
    private ValidationService<Element> validationService;

    @Test
    public void elementProviderIsValid() {
        Element element1 = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement2();
        assertThat(validationService.isValid(element2)).isTrue();
        assertThat(validationService.isValid(element1)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        Element element1 = ModelTestData.getElement1();
        element1.setDefaultPrice(null);
        Element element2 = ModelTestData.getElement2();
        element2.setName(null);
        Element element3 = ModelTestData.getElement2();
        element3.setType(null);

        assertThat(validationService.isValid(element1)).isFalse();
        assertThat(validationService.isValid(element2)).isFalse();
        assertThat(validationService.isValid(element3)).isFalse();
    }

    @Test
    public void elementProviderInvalidWithNonCorrectField() {
        Element element1 = ModelTestData.getElement1();
        element1.setName("       ");
        Element element2 = ModelTestData.getElement1();
        element2.setName("n");
        Element element3 = ModelTestData.getElement1();
        element3.setName(" name ");

        assertThat(validationService.isValid(element1)).isFalse();
        assertThat(validationService.isValid(element2)).isFalse();
        assertThat(validationService.isValid(element3)).isFalse();
    }
}