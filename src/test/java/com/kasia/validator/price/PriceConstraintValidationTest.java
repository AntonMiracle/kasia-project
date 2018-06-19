package com.kasia.validator.price;

import com.kasia.model.price.Price;
import com.kasia.util.TestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriceConstraintValidationTest extends TestHelper<Price> {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private PriceConstraintValidation constraintValidation;
    private Price price;

    @BeforeClass
    public static void beforeClass() throws IOException {
        new TestHelper().copyProductionValidationMessagesPropertiesForTesting();
        validatorFactory = new TestHelper().getValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void afterClass() {
        validatorFactory.close();
    }

    @Before
    public void before() {
        price = new Price();
        constraintValidation = new PriceConstraintValidation();
    }

    // VALID ================================================
    @Test(expected = Test.None.class)
    public void validUser() {
        price.setAmount(100);

        assertThat(validator.validate(price).size()).isEqualTo(0);
    }

    // FIELDS NAME ================================================
    @Test(expected = Test.None.class)
    public void fieldNameIsCorrect() throws NoSuchFieldException {
        price.getClass().getDeclaredField(constraintValidation.AMOUNT);

        assertThat(countFields(price)).isEqualTo(1);
    }

    // TRIM STRING FIELDS ================================================
    @Test
    public void isValidTrimStringFields() throws NoSuchFieldException {
        assertThat(countFields(price, String.class)).isEqualTo(0);
    }

    // AMOUNT ================================================
    @Test
    public void amountInvalidMsg() {
        price.setAmount(-100);
        String errorMsg = "must be 0-" + Long.MAX_VALUE;
        assertThat(mapErrorFieldsWithMsg(validator.validate(price)).get(constraintValidation.AMOUNT)).isEqualTo(errorMsg);
    }

    @Test
    public void amountInvalid() {
        price.setAmount(-100);
        assertThat(mapErrorFieldsWithMsg(validator.validate(price)).containsKey(constraintValidation.AMOUNT)).isTrue();
    }

    @Test
    public void usernameValid() {
        price.setAmount(100);
        assertThat(mapErrorFieldsWithMsg(validator.validate(price)).containsKey(constraintValidation.AMOUNT)).isFalse();
    }
}