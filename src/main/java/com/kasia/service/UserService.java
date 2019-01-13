package com.kasia.service;

import com.kasia.model.User;

import java.time.ZoneId;

public interface UserService extends Service<User> {
    User create(String email, String name, String password, ZoneId zoneId);

    boolean isEmailUnique(String email);

    boolean isNameUnique(String name);

    String crypt(String nonCryptPassword);

    ZoneId zoneIdOf(String zoneId);

    User getByName(String name);

    User getByEmail(String email);
}
