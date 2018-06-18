package com.kasia.validator.user;

import com.kasia.model.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;

public class UserConstraintValidation implements ConstraintValidator<UserConstraint, User> {
    //как добавить в сообщение программно параметры например максимальная и минимальная длинна?
    private int emailMinLength;
    private int emailMaxLength;
    private String emailRegexp;

    private int passwordMinLength;
    private int passwordMaxLength;
    private String passwordRegexp;

    private int usernameMinLength;
    private int usernameMaxLength;
    private String usernameRegexp;

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
        emailMinLength = constraintAnnotation.emailMinLength();
        emailMaxLength = constraintAnnotation.emailMaxLength();
        emailRegexp = constraintAnnotation.emailRegexp();

        passwordMinLength = constraintAnnotation.passwordMinLength();
        passwordMaxLength = constraintAnnotation.passwordMaxLength();
        passwordRegexp = constraintAnnotation.passwordRegexp();

        usernameMinLength = constraintAnnotation.usernameMinLength();
        usernameMaxLength = constraintAnnotation.usernameMaxLength();
        usernameRegexp = constraintAnnotation.usernameRegexp();
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        int errorCount = 0;
        constraintValidatorContext.disableDefaultConstraintViolation();

        trimStringFields(user);
        if (user == null) return true;
        if (!isBudgetsValid(user, constraintValidatorContext)) ++errorCount;
        if (!isCreateValid(user, constraintValidatorContext)) ++errorCount;
        if (!isEmailValid(user, constraintValidatorContext)) ++errorCount;
        if (!isGroupsValid(user, constraintValidatorContext)) ++errorCount;
        if (!isLocaleValid(user, constraintValidatorContext)) ++errorCount;
        if (!isZoneIdValid(user, constraintValidatorContext)) ++errorCount;
        if (!isPasswordValid(user, constraintValidatorContext)) ++errorCount;
        if (!isUsernameValid(user, constraintValidatorContext)) ++errorCount;
        return errorCount == 0;
    }

    private void trimStringFields(User user) {
        if (user.getUsername() != null) user.setUsername(user.getUsername().trim());
        if (user.getPassword() != null) user.setPassword(user.getPassword().trim());
        if (user.getEmail() != null) user.setEmail(user.getEmail().trim());
    }

    private void addConstraintViolation(String fieldName, String errorMsg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.buildConstraintViolationWithTemplate(errorMsg)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }

    private boolean isBudgetsValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String msg;
        if (user.getBudgets() == null) {
            msg = "{validation.user.UserConstraint.message.budgets.empty}";
            addConstraintViolation(BUDGETS, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isUsernameValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String username = user.getUsername();
        String msg;
        if (username == null) {
            msg = "{validation.user.UserConstraint.message.username.empty}";
            addConstraintViolation(USERNAME, msg, constraintValidatorContext);
            return false;
        }
        if (username.length() < usernameMinLength || username.length() > usernameMaxLength || !username.matches(usernameRegexp)) {
            msg = "{validation.user.UserConstraint.message.username.invalid}";
            addConstraintViolation(USERNAME, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String password = user.getPassword();
        String msg;
        if (password == null) {
            msg = "{validation.user.UserConstraint.message.password.empty}";
            addConstraintViolation(PASSWORD, msg, constraintValidatorContext);
            return false;
        }
        if (password.length() < passwordMinLength || password.length() > passwordMaxLength || !password.matches(passwordRegexp)) {
            msg = "{validation.user.UserConstraint.message.password.invalid}";
            addConstraintViolation(PASSWORD, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String email = user.getEmail();
        String msg;
        if (email == null) {
            msg = "{validation.user.UserConstraint.message.email.empty}";
            addConstraintViolation(EMAIL, msg, constraintValidatorContext);
            return false;
        }
        if (email.length() < emailMinLength || email.length() > emailMaxLength || !user.getEmail().matches(emailRegexp)) {
            msg = "{validation.user.UserConstraint.message.email.invalid}";
            addConstraintViolation(EMAIL, msg, constraintValidatorContext);
            return false;
        }

        return true;
    }

    private boolean isZoneIdValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String msg;
        if (user.getZoneId() == null) {
            msg = "{validation.user.UserConstraint.message.zoneId.empty}";
            addConstraintViolation(ZONE_ID, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isLocaleValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String msg;
        if (user.getLocale() == null) {
            msg = "{validation.user.UserConstraint.message.locale.empty}";
            addConstraintViolation(LOCALE, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isCreateValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getCreate() == null) {
            addConstraintViolation(CREATE, "Create date is null", constraintValidatorContext);
            return false;
        }
        if (!user.getCreate().isBefore(Instant.now())) {
            addConstraintViolation(CREATE, "Create date must be in past", constraintValidatorContext);
            return false;
        }
        return true;
    }


    private boolean isGroupsValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getGroups() == null) {
            addConstraintViolation(GROUPS, "Groups is null", constraintValidatorContext);
            return false;
        }
        if (user.getGroups().size() == 0) {
            addConstraintViolation(GROUPS, "Groups must contain at least one system group", constraintValidatorContext);
            return false;
        }
        return true;
    }


}
