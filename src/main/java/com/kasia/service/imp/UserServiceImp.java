package com.kasia.service.imp;

import com.kasia.model.result.Result;
import com.kasia.model.user.User;
import com.kasia.service.UserService;

public class UserServiceImp implements UserService {
    @Override
    public Result<User> updateUser(User user) {
        return null;
    }

    @Override
    public Result<User> addNewUser(User user) {
        return null;
    }

    @Override
    public Result<Boolean> removeUserWithEmail(String email) {
        return null;
    }

    @Override
    public Result<User> getUserWithEmail(String email) {
        return null;
    }
}