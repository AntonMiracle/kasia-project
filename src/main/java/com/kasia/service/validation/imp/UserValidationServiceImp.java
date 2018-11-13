package com.kasia.service.validation.imp;

import com.kasia.model.User;
import com.kasia.service.validation.UserValidationService;

import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UserValidationServiceImp implements UserValidationService {

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
        if (!isEmailValid(user.getEmail())) {
            msg.append("{validation.user.UserConstraint.message.email}");
            addConstraintViolation(msg.toString(), constraintValidatorContext);
            return false;
        }
        if (!isNickValid(user.getNick())) {
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

    private boolean isMatch(String field, Regex regex) {
        return field != null && Pattern.compile(regex.getREGEX()).matcher(field).matches();
    }

    @Override
    public boolean isNonCryptPasswordValid(String password) {
        return password != null && isMatch(password, Regex.PASSWORD);
    }

    @Override
    public boolean isEmailValid(String email) {
        return email != null && isMatch(email, Regex.EMAIL);
    }

    @Override
    public boolean isNickValid(String nick) {
        return nick != null && isMatch(nick, Regex.NICK);
    }
}
