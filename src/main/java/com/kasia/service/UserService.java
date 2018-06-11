package com.kasia.service;

import com.kasia.model.result.Result;
import com.kasia.model.user.User;

public interface UserService {

    Result<User> updateUser(User user);

    Result<User> addNewUser(User user);

    Result<Boolean> removeUserWithEmail(String email);

    Result<User> getUserWithEmail(String email);
}
