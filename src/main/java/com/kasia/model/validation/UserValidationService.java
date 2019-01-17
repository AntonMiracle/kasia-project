package com.kasia.model.validation;

import com.kasia.model.User;

public interface UserValidationService extends ValidationService<User> {

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isNameValid(String name);
}
