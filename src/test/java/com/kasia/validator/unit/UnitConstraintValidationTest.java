package com.kasia.validator.unit;

import com.kasia.model.unit.Unit;
import com.kasia.util.TestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UnitConstraintValidationTest extends TestHelper<Unit> {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private UnitConstraintValidation constraintValidation;
    private Unit unit;

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
        unit = new Unit();
        constraintValidation = new UnitConstraintValidation();
    }

    // VALID ================================================
    @Test(expected = Test.None.class)
    public void validUser() {
        unit.setName("kg");
        assertThat(validator.validate(unit).size()).isEqualTo(0);
        unit.setName("l");
        assertThat(validator.validate(unit).size()).isEqualTo(0);
        unit.setName("ml");
        assertThat(validator.validate(unit).size()).isEqualTo(0);
        unit.setName("g");
        assertThat(validator.validate(unit).size()).isEqualTo(0);
        unit.setName("neto");
        assertThat(validator.validate(unit).size()).isEqualTo(0);
        unit.setName("bruto");
        assertThat(validator.validate(unit).size()).isEqualTo(0);
    }

    // FIELDS NAME ================================================
    @Test(expected = Test.None.class)
    public void fieldNameIsCorrect() throws NoSuchFieldException {
        unit.getClass().getDeclaredField(constraintValidation.NAME);

        assertThat(countFields(unit)).isEqualTo(1);
    }

    // TRIM STRING FIELDS ================================================
    @Test
    public void isValidTrimStringFields() throws NoSuchFieldException {
        assertThat(countFields(unit, String.class)).isEqualTo(1);

        unit.setName(" kg ");
        constraintValidation.isValid(unit, mockConstraintValidatorContext());

        assertThat(unit.getName()).isEqualTo("kg");
    }

    @Test(expected = Test.None.class)
    public void whenFieldsNullIsValidTrimStringFieldsNotThrowException() {
        assertThat(countFields(unit, String.class)).isEqualTo(1);

        unit.setName(null);

        constraintValidation.isValid(unit, mockConstraintValidatorContext());
    }

    @Test
    public void whenFieldsNullIsValidTrimStringFieldsNoChanges() {
        assertThat(countFields(unit, String.class)).isEqualTo(1);

        unit.setName(null);

        constraintValidation.isValid(unit, mockConstraintValidatorContext());

        assertThat(unit.getName()).isNull();
    }

    // NAME ================================================
    @Test
    public void emailInvalidMsg() {
        unit.setName("");
        String errorMsg = "length 1-12 and only symbols A-z,0-9";
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).get(constraintValidation.NAME)).isEqualTo(errorMsg);

        unit.setName(null);
        errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).get(constraintValidation.NAME)).isEqualTo(errorMsg);
    }

    @Test
    public void emailInvalid() {
        unit.setName(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isTrue();

        String name = "";
        unit.setName(name);//length min 1
        assertThat(name.length()).isEqualTo(0);
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isTrue();

        name = "supermegaunit";
        unit.setName(name);//length max 12
        assertThat(name.length()).isEqualTo(13);
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isTrue();

        name = "33";
        unit.setName(name);
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isTrue();

        name = "c3m";
        unit.setName(name);
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isTrue();
    }

    @Test
    public void emailValid() {
        unit.setName("kg");
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isFalse();

        unit.setName("m3");
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isFalse();

        unit.setName("l");
        assertThat(mapErrorFieldsWithMsg(validator.validate(unit)).containsKey(constraintValidation.NAME)).isFalse();
    }

}