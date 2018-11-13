package com.kasia.service.validation;

import com.kasia.model.User;
import com.kasia.service.validation.constraint.UserConstraint;

import javax.validation.ConstraintValidator;

public interface UserValidationService extends ValidationService<User>, ConstraintValidator<UserConstraint, User> {

    boolean isNonCryptPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isNickValid(String nick);

    enum Regex {
        EMAIL("^[A-Za-z0-9+_.-]+@(.+)$"), PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"), NICK("^[A-Za-z0-9+_.-]{3,}$");

        private final String REGEX;

        Regex(String regex) {
            this.REGEX = regex;
        }

        public String getREGEX() {
            return REGEX;
        }
    }
}
