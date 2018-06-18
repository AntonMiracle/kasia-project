package com.kasia.validator.user;

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

    // TEST FIELDS NAME ================================================
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
        String errorMsg = "length 6-32 contain [A-Za-z0-9_]+";
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

}