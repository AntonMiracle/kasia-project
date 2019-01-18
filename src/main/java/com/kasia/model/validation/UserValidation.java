package com.kasia.model.validation;

import com.kasia.model.User;

public interface UserValidation extends Validation<User> {

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isNameValid(String name);
}
