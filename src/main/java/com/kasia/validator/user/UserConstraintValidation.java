package com.kasia.validator.user;

import com.kasia.model.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.util.HashSet;

public class UserConstraintValidation implements ConstraintValidator<UserConstraint, User> {
    private final int EMAIL_MIN_LENGTH = 5;
    private final int EMAIL_MAX_LENGTH = 32;
    private final String EMAIL_REGEXP = ".+[@].+[.].+";

    private final int PASSWORD_MIN_LENGTH = 6;
    private final int PASSWORD_MAX_LENGTH = 32;
    private final String PASSWORD_REGEXP = "[A-Za-z0-9]+";

    private final int USERNAME_MIN_LENGTH = 3;
    private final int USERNAME_MAX_LENGTH = 16;
    private final String USERNAME_REGEXP = "[A-Za-z0-9]+";

    public final String USERNAME = "username";
    public final String PASSWORD = "password";
    public final String GROUPS = "groups";
    public final String EMAIL = "email";
    public final String CREATE = "create";
    public final String LOCALE = "locale";
    public final String ZONE_ID = "zoneId";
    public final String BUDGETS = "budgets";

    @Override
    public void initialize(UserConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        int errorCount = 0;
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (user == null) return true;
        if (user.getBudgets() == null) user.setBudgets(new HashSet<>());
        if (!isCreateValid(user, constraintValidatorContext)) ++errorCount;
        if (!isEmailValid(user, constraintValidatorContext)) ++errorCount;
        if (!isGroupsValid(user, constraintValidatorContext)) ++errorCount;
        if (!isLocaleValid(user, constraintValidatorContext)) ++errorCount;
        if (!isZoneIdValid(user, constraintValidatorContext)) ++errorCount;
        if (!isPasswordValid(user, constraintValidatorContext)) ++errorCount;
        if (!isUsernameValid(user, constraintValidatorContext)) ++errorCount;

        return errorCount == 0;
    }

    private void put(String fieldName, String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.buildConstraintViolationWithTemplate(errorMsg)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }

    private boolean isUsernameValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String username = user.getUsername();
        if (username == null) {
            put("username", "Username is null.{validation.user.UserConstraint.message}", constraintValidatorContext);
            return false;
        }
        if (username.length() < USERNAME_MIN_LENGTH || username.length() > USERNAME_MAX_LENGTH) {
            put("username", "Username length must be " + USERNAME_MIN_LENGTH + "-" + USERNAME_MAX_LENGTH, constraintValidatorContext);
            return false;
        }
        if (!username.matches(USERNAME_REGEXP)) {
            put("username", "Username must content A-Za-z0-9", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String password = user.getPassword();
        if (password == null) {
            put("password", "Password is null", constraintValidatorContext);
            return false;
        }
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            put("password", "Password length must be " + PASSWORD_MIN_LENGTH + "-" + PASSWORD_MAX_LENGTH, constraintValidatorContext);
            return false;
        }
        if (!password.matches(PASSWORD_REGEXP)) {
            put("password", "Password must content A-Za-z0-9", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isZoneIdValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getZoneId() == null) {
            put("zoneId", "ZoneId is null", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isLocaleValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getLocale() == null) {
            put("locale", "Locale is null", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isCreateValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getCreate() == null) {
            put("create", "Create date is null", constraintValidatorContext);
            return false;
        }
        if (!user.getCreate().isBefore(Instant.now())) {
            put("create", "Create date must be in past", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String email = user.getEmail();

        if (email == null) {
            put("email", "Email is null", constraintValidatorContext);
            return false;
        }
        if (email.length() < EMAIL_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH) {
            put("email", "Email must has " + EMAIL_MIN_LENGTH + "-" + EMAIL_MAX_LENGTH + " symbols", constraintValidatorContext);
            return false;
        }
        if (!user.getEmail().matches(EMAIL_REGEXP)) {
            put("email", "Email incorrect", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isGroupsValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getGroups() == null) {
            put("groups", "Groups is null", constraintValidatorContext);
            return false;
        }
        if (user.getGroups().size() == 0) {
            put("groups", "Groups must contain at least one system group", constraintValidatorContext);
            return false;
        }
        return true;
    }


}
