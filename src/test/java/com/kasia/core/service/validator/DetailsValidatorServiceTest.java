package com.kasia.core.service.validator;

import com.kasia.core.model.Details;
import com.kasia.core.service.validator.impl.DetailsValidatorServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DetailsValidatorServiceTest implements HelpTestingValidator<Details> {

    private DetailsValidatorService validatorService;
    private Details details;

    @Before
    public void before() {
        validatorService = new DetailsValidatorServiceImpl();
        details = new Details();
    }

    private String nameField = getField(Details.class,"name");

    @Test
    public void nameWithEmptyStringValid() {
        details.setName("");
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void nameWithNullInvalid() {
        details.setName(null);
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithLengthMoreThanFifteenInvalid() {
        details.setName("Nonasfghfghhgfdg");
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithAlphabetSymbolsValid() {
        details.setName("Azaza");
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void nameWithWhiteSymbolFromStartInvalid() {
        details.setName("  Azaza");
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithWhiteSymbolInTheEndInvalid() {
        details.setName("Azaza  ");
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameContainsMoreWordsThanOneInvalid() {
        details.setName("Azaza Zaaza");
        long count = countConstraintViolation(validatorService,details, nameField);
        assertThat(count == 1).isTrue();
    }

    private String surnameField = getField(Details.class,"surname");

    @Test
    public void surnameWithEmptyStringValid() {
        details.setSurname("");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void surnameWithLengthMoreThan25Invalid() {
        details.setSurname("awwwweeeeewwwwweeeeewwwwwV");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithAlphabetSymbolsValid() {
        details.setSurname("Agatera");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void surnameContainsTwoWordsValid() {
        details.setSurname("First Second");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void surnameHasDashBetweenTwoWordsValid() {
        details.setSurname("First-Second");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void surnameWithFirstWordLengthMoreThan25Invalid() {
        details.setSurname("FirstfirstfirstdddddfffffT Second");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithSecondWordLengthMoreThan25Invalid() {
        details.setSurname("First SecondsecondsecooooodddddV");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithExtraSpacesBetweenWordsInvalid() {
        details.setSurname("First  Second");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithSpaceFromStartInvalid() {
        details.setSurname(" Surname");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithSpaceInTheEndInvalid() {
        details.setSurname("Surname ");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithExtraDashesBetweenWordsInvalid() {
        details.setSurname("First--Second");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithDashFromStartInvalid() {
        details.setSurname("-Surname");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithDashInTheEndInvalid() {
        details.setSurname("Surname-");
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void surnameWithNullInvalid() {
        details.setSurname(null);
        long count = countConstraintViolation(validatorService,details, surnameField);
        assertThat(count == 1).isTrue();
    }

    private String positionField = getField(Details.class,"position");

    @Test
    public void positionWithLengthMoreThan30Invalid() {
        details.setPosition("awwwweeeeewwwwweeeeewwwwwwwwwwV");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithAlphabetSymbolsValid() {
        details.setPosition("Position");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void positionContainsTwoWordsValid() {
        details.setPosition("Firstposition Secondposition");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void positionHasDashBetweenTwoWordsValid() {
        details.setPosition("Firstposition-Secondposition");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void positionWithFirstWordLengthMoreThan25Invalid() {
        details.setPosition("FirstfirstfirstdddddfffffT Secondposition");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithSecondWordLengthMoreThan25Invalid() {
        details.setPosition("Firstposition SecondsecondsecooooodddddV");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithExtraSpacesBetweenWordsInvalid() {
        details.setPosition("Firstposition  Secondposition");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithSpaceFromStartInvalid() {
        details.setPosition(" Position");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithSpaceInTheEndInvalid() {
        details.setPosition("Position ");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithExtraDashesBetweenWordsInvalid() {
        details.setPosition("Position--Position");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithDashFromStartInvalid() {
        details.setPosition("-Position");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithDashInTheEndInvalid() {
        details.setPosition("Position-");
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void positionWithNullInvalid() {
        details.setPosition(null);
        long count = countConstraintViolation(validatorService,details, positionField);
        assertThat(count == 1).isTrue();
    }

    private String nickField = getField(Details.class,"nick");

    @Test
    public void nickWithDashValid() {
        details.setNick("_Nick_2_");
        long count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 0).isTrue();
        details.setNick("-Nick-2-");
        count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void nickWithNullInvalid() {
        details.setNick(null);
        long count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nickWithLengthLessThanFiveInvalid() {
        details.setNick("Nick");
        long count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nickWithLengthMoreThan20Invalid() {
        details.setNick("NickNickNickNickNickT");
        long count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nickWithDashLengthMoreThan20Invalid() {
        details.setNick("NickNickNick-NickNicK");
        long count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nickWithNotOnlyNumbersAndAlphabetsSymbolsInvalid() {
        details.setNick("Nick23$");
        long count = countConstraintViolation(validatorService,details, nickField);
        assertThat(count == 1).isTrue();
    }


    private String emailField = getField(Details.class,"email");

    @Test
    public void emailWithNullInvalid() {
        details.setEmail(null);
        long count = countConstraintViolation(validatorService,details, emailField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void emailWithLengthLessThan5Invalid() {
        details.setEmail("n@ol");
        long count = countConstraintViolation(validatorService,details, emailField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void emailWithLengthMoreThan64Invalid() {
        details.setEmail("mailmailmailmailmailmailma@lmailmailmailmailmailmailmailmailm.ilF");
        long count = countConstraintViolation(validatorService,details, emailField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void detailsWithValidFieldsValid() {
        details.setEmail("hej@gmail.com");
        details.setNick("Tommy");
        details.setPosition("Menedger");
        details.setSurname("Surname");
        details.setName("Name");
        assertThat(validatorService.isValid(details)).isTrue();
    }

    /**
     * ??????????????????????????????????
     */
    @Test
    public void validatorTrimDetailsFields() {
        details.setEmail(" hej@gmail.com ");
        details.setNick(" Tommy ");
        details.setPosition("  Menedger  ");
        details.setSurname(" Surname ");
        details.setName("Name ");
        validatorService.trimFields(details);
        assertThat(validatorService.isValid(details)).isTrue();
    }

    @Test
    public void detailsHas6Fields() {
        assertThat(countFields(details)).isEqualTo(6);
    }

}
