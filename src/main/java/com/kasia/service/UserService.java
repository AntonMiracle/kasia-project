package com.kasia.service;

import com.kasia.model.User;

import java.util.Locale;

public interface UserService extends ValidationService<User> {
    boolean isEmailUnique(String email);

    boolean isNameUnique(String name);

    String crypt(String nonCryptPassword);

    boolean isZoneIdExist(String zoneId);

    boolean isLocaleExist(Locale locale);

    User save(User user);

    boolean delete(User user);

    User getById(long id);

    User getByName(String name);

    User getByEmail(String email);
}
