package com.kasia.core.service.validator.impl;

import com.kasia.core.model.User;
import com.kasia.core.service.validator.UserValidatorService;

public class UserValidatorServiceImpl implements UserValidatorService {

    @Override
    public void trimFields(User user) {
        if (user == null) return;
        if (user.getLogin() != null) user.setLogin(user.getLogin().trim());
        if (user.getPassword() != null) user.setPassword(user.getPassword().trim());
    }
}
