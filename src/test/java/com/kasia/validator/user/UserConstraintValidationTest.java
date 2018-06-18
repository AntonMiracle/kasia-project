package com.kasia.validator.user;

import com.kasia.model.group.Group;
import com.kasia.model.user.User;
import com.kasia.service.ValidationService;
import com.kasia.service.imp.ValidationServiceImp;
import com.kasia.util.TestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserConstraintValidationTest extends TestHelper {
    private static ValidatorFactory validatorFactory;
    private static ValidationService<User> service;
    private static Validator validator;
    private UserConstraintValidation constraintValidation;
    private User user;

    @BeforeClass
    public static void beforeClass() throws IOException {
        new TestHelper().copyProductionValidationMessagesPropertiesForTesting();

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
        user = new User();
        constraintValidation = new UserConstraintValidation();
    }

    // VALID ================================================
    @Test(expected = Test.None.class)
    public void validUser() {
        user.setUsername("Username22");
        user.setCreate(Instant.now());

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
    public void fieldNameInUserConstraintValidationCorrect() throws NoSuchFieldException {
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
    private ConstraintValidatorContext mockConstraintValidatorContext() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext node = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);
        when(context.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(builder);
        when(builder.addPropertyNode(any(String.class))).thenReturn(node);
        when(node.addConstraintViolation()).thenReturn(context);
        return context;
    }

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
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.USERNAME)).isEqualTo(errorMsg);

        user.setUsername(null);
        errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.USERNAME)).isEqualTo(errorMsg);
    }

    @Test
    public void usernameInvalid() {
        user.setUsername(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isTrue();

        String username = "usern";
        user.setUsername(username);//length min 6
        assertThat(username.length()).isEqualTo(5);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isTrue();

        username = "usernameeeusernameeeusernameeeuuu";
        user.setUsername(username);//length max 32
        assertThat(username.length()).isEqualTo(33);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isTrue();
    }

    @Test
    public void usernameValid() {
        user.setUsername("Username23");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("Username");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("username");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("uSeRnAmE");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("22uSeRnAmE");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isFalse();

        user.setUsername("22_uSeRnAmE");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.USERNAME)).isFalse();
    }

    // PASSWORD ================================================
    @Test
    public void passwordInvalidMsg() {
        user.setPassword("");
        String errorMsg = "length 6-32 with upper and lower case symbol and number";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.PASSWORD)).isEqualTo(errorMsg);

        user.setPassword(null);
        errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.PASSWORD)).isEqualTo(errorMsg);
    }

    @Test
    public void passwordInvalid() {
        user.setPassword(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        String password = "Pa2sw";
        user.setPassword(password);//length min 6
        assertThat(password.length()).isEqualTo(5);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "2passWord22passWord22passWord2223";
        user.setPassword(password);//length max 32
        assertThat(password.length()).isEqualTo(33);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "password";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "PASSWORD";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "99999999";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "AaaaaaA";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "aAAAAAa";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "9aaaaa9";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "a99aa99a";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "A99999A";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();

        password = "9AAAAA9";
        user.setPassword(password);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isTrue();
    }

    @Test
    public void passwordValid() {
        user.setPassword("Passsd2");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("2assssP");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("aPsss2s");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("a2sssPs");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("a22ssPPs");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isFalse();

        user.setPassword("aPPsss22s");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.PASSWORD)).isFalse();
    }

    // EMAIL ================================================
    @Test
    public void emailInvalidMsg() {
        user.setEmail("");
        String errorMsg = "length 6-32 or mail incorrect";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.EMAIL)).isEqualTo(errorMsg);

        user.setEmail(null);
        errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.EMAIL)).isEqualTo(errorMsg);
    }

    @Test
    public void emailInvalid() {
        user.setEmail(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isTrue();

        String email = "e@d.d";
        user.setEmail(email);//length min 6
        assertThat(email.length()).isEqualTo(5);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isTrue();

        email = "longestmaileverseen@supermail.com";
        user.setEmail(email);//length max 32
        assertThat(email.length()).isEqualTo(33);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isTrue();

        email = "email.com";
        user.setEmail(email);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isTrue();

        email = "email@com";
        user.setEmail(email);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isTrue();
    }

    @Test
    public void emailValid() {
        user.setEmail("email@gmail.com");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isFalse();

        user.setEmail("e_ma_il@gmail.com");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isFalse();

        user.setEmail("e_m22a_il@gmail.com");
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.EMAIL)).isFalse();
    }

    // BUDGETS ================================================
    @Test
    public void budgetInvalidMsg() {
        user.setBudgets(null);
        String errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.BUDGETS)).isEqualTo(errorMsg);
    }

    @Test
    public void budgetInvalid() {
        user.setBudgets(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.BUDGETS)).isTrue();
    }

    @Test
    public void budgetValid() {
        user.setBudgets(new HashSet<>());
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.BUDGETS)).isFalse();
    }

    // LOCALE ================================================
    @Test
    public void localeInvalidMsg() {
        user.setLocale(null);
        String errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.LOCALE)).isEqualTo(errorMsg);
    }

    @Test
    public void localeInvalid() {
        user.setLocale(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.LOCALE)).isTrue();
    }

    @Test
    public void localeValid() {
        user.setLocale(Locale.getDefault());
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.LOCALE)).isFalse();
    }

    // ZONE ID ================================================
    @Test
    public void zoneIdInvalidMsg() {
        user.setZoneId(null);
        String errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.ZONE_ID)).isEqualTo(errorMsg);
    }

    @Test
    public void zoneIdInvalid() {
        user.setZoneId(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.ZONE_ID)).isTrue();
    }

    @Test
    public void zoneIdValid() {
        user.setZoneId(ZoneId.systemDefault());
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.ZONE_ID)).isFalse();
    }

    // GROUPS ================================================
    @Test
    public void groupsInvalidMsg() {
        user.setGroups(null);
        String errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.GROUPS)).isEqualTo(errorMsg);

        user.setGroups(new HashSet<>());
        errorMsg = "at least one system group";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.GROUPS)).isEqualTo(errorMsg);
    }

    @Test
    public void groupsInvalid() {
        user.setGroups(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.GROUPS)).isTrue();

        user.setGroups(new HashSet<>());
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.GROUPS)).isTrue();
    }

    @Test
    public void groupsValid() {
        Set<Group> groups = new HashSet<>();
        groups.add(new Group());
        user.setGroups(groups);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.GROUPS)).isFalse();
    }

    // CREATE ================================================
    @Test
    public void createInvalidMsg() {
        user.setCreate(null);
        String errorMsg = "null";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.CREATE)).isEqualTo(errorMsg);

        user.setCreate(Instant.now().plusSeconds(1000));
        errorMsg = "future";
        assertThat(service.mapErrorFieldsWithMsg(user).get(constraintValidation.CREATE)).isEqualTo(errorMsg);
    }

    @Test
    public void createInvalid() {
        user.setCreate(null);
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.CREATE)).isTrue();

        user.setCreate(Instant.now().plusSeconds(1000));
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.CREATE)).isTrue();
    }

    @Test
    public void createValid() {
        user.setCreate(Instant.now().minusSeconds(100));
        assertThat(service.mapErrorFieldsWithMsg(user).containsKey(constraintValidation.CREATE)).isFalse();
    }
}