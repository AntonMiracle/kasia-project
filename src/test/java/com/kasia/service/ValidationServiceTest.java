package com.kasia.service;

import com.kasia.model.Model;
import com.kasia.service.imp.ValidationServiceImp;
import com.kasia.util.TestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidationServiceTest {
    private static ValidatorFactory validatorFactory;
    private static ValidationService<TestValidationObject> service;
    private static Validator validator;
    private TestValidationObject testValidationObject;

    @BeforeClass
    public static void beforeClass() throws IOException {
        validatorFactory = new TestHelper().getValidatorFactory();
        service = new ValidationServiceImp<>(validatorFactory.getValidator());
        validator = service.getValidator();
    }

    @AfterClass
    public static void afterClass() {
        validatorFactory.close();
    }

    @Before
    public void before() {
        testValidationObject = new TestValidationObject();
    }

    @Test
    public void validateReturn2Errors() {
        assertThat(validator.validate(testValidationObject).size()).isEqualTo(2);
    }

    @Test
    public void mapErrorFieldsWithMsg() {
        Map<String, String> errorMap = service.mapErrorFieldsWithMsg(new TestValidationObject());
        assertThat(errorMap.get(testValidationObject.FIELD1)).isEqualTo("must not be null");
        assertThat(errorMap.get(testValidationObject.FIELD2)).isEqualTo("must be greater than or equal to 10");
    }
}

class TestValidationObject extends Model {
    final String FIELD1 = "name";
    final String FIELD2 = "value";
    @NotNull
    private String name;
    @Min(10)
    private int value;

    TestValidationObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}