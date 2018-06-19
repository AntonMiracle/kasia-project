package com.kasia.validator.user;

import com.kasia.model.group.Group;
import com.kasia.model.user.User;
import com.kasia.util.TestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserConstraintValidationTest extends TestHelper<User> {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private UserConstraintValidation constraintValidation;
    private User user;

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
        user = new User();
        constraintValidation = new UserConstraintValidation();
    }

    // VALID ================================================
    @Test(expected = Test.None.class)
    public void validUser() {
        user.setUsername("Username22");
        user.setCreate(Instant.now().minusSeconds(100));

        Set<Group> groups = new HashSet<>();
        groups.add(new Group());
        user.setGroups(groups);

        user.setZoneId(ZoneId.systemDefault());
        user.setLocale(Locale.getDefault());
        user.setBudgets(new HashSet<>());
        user.setEmail("email@gmail.com");
        user.setPassword("2Password");

        assertThat(validator.validate(user).size()).isEqualTo(0);
    }

    // FIELDS NAME ================================================
    @Test(expected = Test.None.class)
    public void fieldNameIsCorrect() throws NoSuchFieldException {
        user.getClass().getDeclaredField(constraintValidation.USERNAME);
        user.getClass().getDeclaredField(constraintValidation.PASSWORD);
        user.getClass().getDeclaredField(constraintValidation.EMAIL);
        user.getClass().getDeclaredField(constraintValidation.GROUPS);
        user.getClass().getDeclaredField(constraintValidation.BUDGETS);
        user.getClass().getDeclaredField(constraintValidation.CREATE);
        user.getClass().getDeclaredField(constraintValidation.LOCALE);
        user.getClass().getDeclaredField(constraintValidation.ZONE_ID);

        assertThat(countFields(user)).isEqualTo(8);
    }

    // TRIM STRING FIELDS ================================================
    @Test
    public void isValidTrimStringFields() throws NoSuchFieldException {
        assertThat(countFields(user, String.class)).isEqualTo(3);

        user.setUsername(" Username22 ");
        user.setPassword("  Username22 ");
        user.setEmail("  Username22 ");
        constraintValidation.isValid(user, mockConstraintValidatorContext());

        assertThat(user.getUsername()).isEqualTo("Username22");
        assertThat(user.getPassword()).isEqualTo("Username22");
        assertThat(user.getEmail()).isEqualTo("Username22");
    }

    @Test(expected = Test.None.class)
    public void whenFieldsNullIsValidTrimStringFieldsNotThrowException() {
        assertThat(countFields(user, String.class)).isEqualTo(3);

        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        constraintValidation.isValid(user, mockConstraintValidatorContext());
    }

    @Test
    public void whenFieldsNullIsValidTrimStringFieldsNoChanges() {
        assertThat(countFields(user, String.class)).isEqualTo(3);

        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);

        constraintValidation.isValid(user, mockConstraintValidatorContext());

        assertThat(user.getUsername()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getEmail()).isNull();
    }

    // USERNAME ================================================
    @Test
    public void usernameInvalidMsg() {
        user.setUsername("");
        String errorMsg = "length 6-32 only A-z,0-9,_";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.USERNAME)).isEqualTo(errorMsg);

        user.setUsername(null);
        errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.USERNAME)).isEqualTo(errorMsg);
    }

    @Test
    public void usernameInvalid() {
        user.setUsername(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isTrue();

        String username = "usern";
        user.setUsername(username);//length min 6
        assertThat(username.length()).isEqualTo(5);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isTrue();

        username = "usernameeeusernameeeusernameeeuuu";
        user.setUsername(username);//length max 32
        assertThat(username.length()).isEqualTo(33);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isTrue();
    }

    @Test
    public void usernameValid() {
        user.setUsername("Username23");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("Username");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("username");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("uSeRnAmE");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("22uSeRnAmE");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("22_uSeRnAmE");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.USERNAME)).isFalse();
    }

    // PASSWORD ================================================
    @Test
    public void passwordInvalidMsg() {
        user.setPassword("");
        String errorMsg = "length 6-32 with upper and lower case symbol and number";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.PASSWORD)).isEqualTo(errorMsg);

        user.setPassword(null);
        errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.PASSWORD)).isEqualTo(errorMsg);
    }

    @Test
    public void passwordInvalid() {
        user.setPassword(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        String password = "Pa2sw";
        user.setPassword(password);//length min 6
        assertThat(password.length()).isEqualTo(5);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "2passWord22passWord22passWord2223";
        user.setPassword(password);//length max 32
        assertThat(password.length()).isEqualTo(33);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "password";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "PASSWORD";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "99999999";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "AaaaaaA";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "aAAAAAa";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "9aaaaa9";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "a99aa99a";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "A99999A";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "9AAAAA9";
        user.setPassword(password);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isTrue();
    }

    @Test
    public void passwordValid() {
        user.setPassword("Passsd2");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("2assssP");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("aPsss2s");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("a2sssPs");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("a22ssPPs");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("aPPsss22s");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.PASSWORD)).isFalse();
    }

    // EMAIL ================================================
    @Test
    public void emailInvalidMsg() {
        user.setEmail("");
        String errorMsg = "length 6-32 or mail incorrect";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.EMAIL)).isEqualTo(errorMsg);

        user.setEmail(null);
        errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.EMAIL)).isEqualTo(errorMsg);
    }

    @Test
    public void emailInvalid() {
        user.setEmail(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isTrue();

        String email = "e@d.d";
        user.setEmail(email);//length min 6
        assertThat(email.length()).isEqualTo(5);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isTrue();

        email = "longestmaileverseen@supermail.com";
        user.setEmail(email);//length max 32
        assertThat(email.length()).isEqualTo(33);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isTrue();

        email = "email.com";
        user.setEmail(email);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isTrue();

        email = "email@com";
        user.setEmail(email);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isTrue();
    }

    @Test
    public void emailValid() {
        user.setEmail("email@gmail.com");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isFalse();

        user.setEmail("e_ma_il@gmail.com");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isFalse();

        user.setEmail("e_m22a_il@gmail.com");
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.EMAIL)).isFalse();
    }

    // BUDGETS ================================================
    @Test
    public void budgetInvalidMsg() {
        user.setBudgets(null);
        String errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.BUDGETS)).isEqualTo(errorMsg);
    }

    @Test
    public void budgetInvalid() {
        user.setBudgets(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.BUDGETS)).isTrue();
    }

    @Test
    public void budgetValid() {
        user.setBudgets(new HashSet<>());
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.BUDGETS)).isFalse();
    }

    // LOCALE ================================================
    @Test
    public void localeInvalidMsg() {
        user.setLocale(null);
        String errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.LOCALE)).isEqualTo(errorMsg);
    }

    @Test
    public void localeInvalid() {
        user.setLocale(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.LOCALE)).isTrue();
    }

    @Test
    public void localeValid() {
        user.setLocale(Locale.getDefault());
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.LOCALE)).isFalse();
    }

    // ZONE ID ================================================
    @Test
    public void zoneIdInvalidMsg() {
        user.setZoneId(null);
        String errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.ZONE_ID)).isEqualTo(errorMsg);
    }

    @Test
    public void zoneIdInvalid() {
        user.setZoneId(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.ZONE_ID)).isTrue();
    }

    @Test
    public void zoneIdValid() {
        user.setZoneId(ZoneId.systemDefault());
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.ZONE_ID)).isFalse();
    }

    // GROUPS ================================================
    @Test
    public void groupsInvalidMsg() {
        user.setGroups(null);
        String errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.GROUPS)).isEqualTo(errorMsg);

        user.setGroups(new HashSet<>());
        errorMsg = "at least one system group";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.GROUPS)).isEqualTo(errorMsg);
    }

    @Test
    public void groupsInvalid() {
        user.setGroups(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.GROUPS)).isTrue();

        user.setGroups(new HashSet<>());
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.GROUPS)).isTrue();
    }

    @Test
    public void groupsValid() {
        Set<Group> groups = new HashSet<>();
        groups.add(new Group());
        user.setGroups(groups);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.GROUPS)).isFalse();
    }

    // CREATE ================================================
    @Test
    public void createInvalidMsg() {
        user.setCreate(null);
        String errorMsg = "null";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.CREATE)).isEqualTo(errorMsg);

        user.setCreate(Instant.now().plusSeconds(1000));
        errorMsg = "future";
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).get(constraintValidation.CREATE)).isEqualTo(errorMsg);
    }

    @Test
    public void createInvalid() {
        user.setCreate(null);
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.CREATE)).isTrue();

        user.setCreate(Instant.now().plusSeconds(1000));
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.CREATE)).isTrue();
    }

    @Test
    public void createValid() {
        user.setCreate(Instant.now().minusSeconds(100));
        assertThat(mapErrorFieldsWithMsg(validator.validate(user)).containsKey(constraintValidation.CREATE)).isFalse();
    }
}