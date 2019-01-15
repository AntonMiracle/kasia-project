package com.kasia.validation;

import com.kasia.ConfigurationSpringForTest;
import com.kasia.ModelTestData;
import com.kasia.model.ElementProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementProviderValidationServiceIT extends ConfigurationSpringForTest {
    @Autowired
    private ValidationService<ElementProvider> validationService;

    @Test
    public void elementProviderIsValid() {
        ElementProvider elementProvider1 = ModelTestData.getElementProvider1();
        ElementProvider elementProvider2 = ModelTestData.getElementProvider2();

        assertThat(validationService.isValid(elementProvider1)).isTrue();
        assertThat(validationService.isValid(elementProvider2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        ElementProvider elementProvider1 = ModelTestData.getElementProvider1();
        elementProvider1.setName(null);
        ElementProvider elementProvider2 = ModelTestData.getElementProvider1();
        elementProvider2.setDescription(null);

        assertThat(validationService.isValid(elementProvider1)).isFalse();
        assertThat(validationService.isValid(elementProvider2)).isFalse();
    }

    @Test
    public void elementProviderInvalidWithNonCorrectField() {
        ElementProvider elementProvider1 = ModelTestData.getElementProvider1();
        elementProvider1.setName("       ");
        ElementProvider elementProvider2 = ModelTestData.getElementProvider1();
        elementProvider2.setName("n");
        ElementProvider elementProvider3 = ModelTestData.getElementProvider1();
        elementProvider3.setName(" name ");
        ElementProvider elementProvider4 = ModelTestData.getElementProvider1();
        elementProvider4.setDescription("           ");
        ElementProvider elementProvider5 = ModelTestData.getElementProvider1();
        elementProvider5.setDescription("     description is not correct      ");
        ElementProvider elementProvider6 = ModelTestData.getElementProvider1();
        elementProvider6.setDescription("");

        assertThat(validationService.isValid(elementProvider1)).isFalse();
        assertThat(validationService.isValid(elementProvider2)).isFalse();
        assertThat(validationService.isValid(elementProvider3)).isFalse();
        assertThat(validationService.isValid(elementProvider4)).isFalse();
        assertThat(validationService.isValid(elementProvider5)).isFalse();
        assertThat(validationService.isValid(elementProvider6)).isFalse();
    }
}