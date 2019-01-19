package com.kasia.model.validation;

import com.kasia.ModelTestData;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationValidationIT {
    @Autowired
    private OperationValidation validationService;

    @Test
    public void elementProviderIsValid() {
        Operation operation1 = ModelTestData.getOperation1();
        Operation operation2 = ModelTestData.getOperation2();

        assertThat(validationService.isValid(operation1)).isTrue();
        assertThat(validationService.isValid(operation2)).isTrue();
    }

    @Test
    public void elementProviderInvalidWithNull() {
        Operation operation1 = ModelTestData.getOperation1();
        operation1.setUser(null);
        Operation operation2 = ModelTestData.getOperation1();
        operation2.setCreateOn(null);
        Operation operation3 = ModelTestData.getOperation1();
        operation3.setElement(null);
        Operation operation4 = ModelTestData.getOperation1();
        operation4.setElementProvider(null);
        Operation operation5 = ModelTestData.getOperation1();
        operation5.setPrice(null);

        assertThat(validationService.isValid(operation1)).isFalse();
        assertThat(validationService.isValid(operation2)).isFalse();
        assertThat(validationService.isValid(operation3)).isFalse();
        assertThat(validationService.isValid(operation4)).isFalse();
        assertThat(validationService.isValid(operation5)).isFalse();
    }

    @Test
    public void elementProviderInvalidWithNonCorrectField() {
        Operation operation1 = ModelTestData.getOperation1();
        operation1.setCreateOn(ModelTestData.getNow().plusDays(2));

        assertThat(validationService.isValid(operation1)).isFalse();
    }

    private Operation getOperationWithValidIdInside() {
        Operation operation1 = ModelTestData.getOperation1();
        operation1.getUser().setId(2);
        operation1.getElement().setId(2);
        operation1.getElementProvider().setId(2);
        return operation1;
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidVerifyPositiveIdInsideThrowException() {
        Operation operation1 = getOperationWithValidIdInside();
        operation1.getUser().setId(0);

        validationService.verifyPositiveIdInside(operation1);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementIdInvalidVerifyPositiveIdInsideThrowException() {
        Operation operation1 = getOperationWithValidIdInside();
        operation1.getElement().setId(0);

        validationService.verifyPositiveIdInside(operation1);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementProviderIdInvalidVerifyPositiveIdInsideThrowException() {
        Operation operation1 = getOperationWithValidIdInside();
        operation1.getElementProvider().setId(0);

        validationService.verifyPositiveIdInside(operation1);
    }

}