package com.kasia.validation.user;

import com.kasia.model.User;
import com.kasia.validation.ValidationHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidation implements ConstraintValidator<UserConstraint, User> {
    private ValidationHelper helper = new ValidationHelper();

    // validation email, validation nick, validation password
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) return true;
        StringBuilder msg = new StringBuilder();
        if (user.getEmail() == null || !isEmailValid(user.getEmail())) {
            msg.append("{validation.budget.UserConstraint.message.email}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getNick() == null || !isNickValid(user.getNick())) {
            msg.append("{validation.budget.UserConstraint.message.nick}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (user.getPassword() == null) {
            msg.append("{validation.budget.UserConstraint.message.password}");
            helper.addConstraintViolation(msg.toString(), constraintValidatorContext);
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
