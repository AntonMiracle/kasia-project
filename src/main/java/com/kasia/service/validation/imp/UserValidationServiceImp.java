package com.kasia.service.validation.imp;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.model.Employer;
import com.kasia.model.User;
import com.kasia.service.validation.UserValidationService;
import com.kasia.service.validation.field.UField;
import com.kasia.service.validation.message.UMessageLink;
import com.kasia.service.validation.regex.URegex;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;


public class UserValidationServiceImp implements UserValidationService {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) return true;
        int errors = 0;
        if (user.getId() < 0) {
            addConstraintViolation(UField.ID, UMessageLink.ID_NEGATIVE, constraintValidatorContext);
            errors++;
        }
        if (!isRolesValid(user.getRoles(), constraintValidatorContext)) {
            errors++;
        }
        if (!isEmailValid(user.getEmail(), constraintValidatorContext)) {
            errors++;
        }
        if (!isNickValid(user.getNick(), constraintValidatorContext)) {
            errors++;
        }
        if (!isPasswordValid(user.getPassword(), constraintValidatorContext)) {
            errors++;
        }
        if (user.getZoneId() == null) {
            addConstraintViolation(UField.ZONE_ID, UMessageLink.ZONE_ID_NULL, constraintValidatorContext);
            errors++;
        }
        if (user.getCreateOn() == null) {
            addConstraintViolation(UField.CREATE_ON, UMessageLink.CREATE_ON_NULL, constraintValidatorContext);
            errors++;
        }
        if (user.getArticles() == null) {
            addConstraintViolation(UField.ARTICLES, UMessageLink.ARTICLES_NULL, constraintValidatorContext);
            errors++;
        }
        if (user.getBudgets() == null) {
            addConstraintViolation(UField.BUDGETS, UMessageLink.BUDGETS_NULL, constraintValidatorContext);
            errors++;
        }
        if (user.getEmployers() == null) {
            addConstraintViolation(UField.EMPLOYERS, UMessageLink.EMPLOYERS_NULL, constraintValidatorContext);
            errors++;
        }
        return errors == 0;
    }

    private boolean isPasswordValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) {
            addConstraintViolation(UField.PASSWORD, UMessageLink.PASSWORD_NULL, constraintValidatorContext);
            return false;
        }
        if (!isMatch(password, URegex.PASSWORD)) {
            addConstraintViolation(UField.PASSWORD, UMessageLink.PASSWORD_REGEX_ERROR, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isRolesValid(Set<User.Role> roles, ConstraintValidatorContext constraintValidatorContext) {
        if (roles == null) {
            addConstraintViolation(UField.ROLES, UMessageLink.ROLES_NULL, constraintValidatorContext);
            return false;
        }
        if (!roles.contains(User.Role.USER)) {
            addConstraintViolation(UField.ROLES, UMessageLink.ROLES_NEEDED_USER_ROLE, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            addConstraintViolation(UField.EMAIL, UMessageLink.EMAIL_NULL, constraintValidatorContext);
            return false;
        }
        if (!isMatch(email, URegex.EMAIL)) {
            addConstraintViolation(UField.EMAIL, UMessageLink.EMAIL_REGEX_ERROR, constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean isNickValid(String nick, ConstraintValidatorContext constraintValidatorContext) {
        if (nick == null) {
            addConstraintViolation(UField.NICK, UMessageLink.NICK_NULL, constraintValidatorContext);
            return false;
        }
        if (!isMatch(nick, URegex.NICK)) {
            addConstraintViolation(UField.NICK, UMessageLink.NICK_REGEX_ERROR, constraintValidatorContext);
            return false;
        }
        return true;
    }

    @Override
    public User createModelWithFieldAndValue(UField field, Object value) throws FieldNotExistRuntimeException {
        if (value == null || field == null) throw new NullPointerException("Field or value is null");
        User user = new User();
        switch (field) {
            case ID:
                user.setId((Long) value);
                break;
            case NICK:
                user.setNick((String) value);
                break;
            case EMAIL:
                user.setEmail((String) value);
                break;
            case ROLES:
                user.setRoles((Set<User.Role>) value);
                break;
            case BUDGETS:
                user.setBudgets((Set<Budget>) value);
                break;
            case ZONE_ID:
                user.setZoneId((ZoneId) value);
                break;
            case ARTICLES:
                user.setArticles((Set<Article>) value);
                break;
            case PASSWORD:
                user.setPassword((String) value);
                break;
            case CREATE_ON:
                user.setCreateOn((LocalDateTime) value);
                break;
            case EMPLOYERS:
                user.setEmployers((Set<Employer>) value);
                break;
            default:
                throw new FieldNotExistRuntimeException();
        }
        return user;
    }
}
