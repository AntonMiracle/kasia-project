package com.kasia.model.service;

import com.kasia.model.User;

import java.time.ZoneId;

public interface UserService extends Service<User> {
    User create(String email, String name, String password, ZoneId zoneId);

    boolean isEmailUnique(String email);

    boolean isNameUnique(String name);

    String crypt(String nonCryptPassword);

    ZoneId zoneIdOf(String zoneId);

    User findByName(String name);

    User findByEmail(String email);

    boolean isActivated(User user);

    boolean activate(User user);

    boolean deactivate(User user);

}
