package com.kasia.validation.user;

import com.kasia.model.User;
import com.kasia.validation.ConstraintViolationManager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidation implements ConstraintValidator<UserConstraint, User>, ConstraintViolationManager {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) return true;
        StringBuilder msg = new StringBuilder();
        if (user.getId() < 0) {
            msg.append("{validation.message.id}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getRoles() == null) {
            msg.append("{validation.user.UserConstraint.message.norole}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getEmail() == null || !isEmailValid(user.getEmail())) {
            msg.append("{validation.user.UserConstraint.message.email}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getNick() == null || !isNickValid(user.getNick())) {
            msg.append("{validation.user.UserConstraint.message.nick}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getPassword() == null) {
            msg.append("{validation.user.UserConstraint.message.password}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getZoneId() == null) {
            msg.append("{validation.user.UserConstraint.message.zoneid}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getCreateOn() == null) {
            msg.append("{validation.user.UserConstraint.message.date}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        return true;
    }

    public boolean isNonCryptPasswordValid(String password) {
        if (password == null) return true;
        return true;
    }

    public boolean isEmailValid(String email) {
        if (email == null) return true;
        return true;
    }

    public boolean isNickValid(String nick) {
        if (nick == null) return true;
        return true;
    }
}
