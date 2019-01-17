package com.kasia.model.validation;

import com.kasia.ModelTestData;
import com.kasia.model.ElementProvider;
import com.kasia.model.validation.ElementProviderValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElementProviderValidationServiceIT {
    @Autowired
    private ElementProviderValidationService validationService;

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

    @Test
    public void descriptionIsValid() {
        int maxLength = 250;
        StringBuilder description = new StringBuilder();
        while (maxLength-- > 0) description.append("a");

        ElementProvider elementProvider1 = ModelTestData.getElementProvider1();
        elementProvider1.setDescription(description.toString());
        ElementProvider elementProvider2 = ModelTestData.getElementProvider1();
        elementProvider2.setDescription("aa");

        assertThat(validationService.isValid(elementProvider1)).isTrue();
        assertThat(validationService.isValid(elementProvider2)).isTrue();
    }
}