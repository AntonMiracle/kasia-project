package com.kasia.service;

import com.kasia.model.User;

import java.time.ZoneId;
import java.util.Locale;

public interface UserService extends CRUDService<User> {
    User create(String email, String name, String password, ZoneId zoneId, Locale locale);

    boolean isEmailUnique(String email);

    boolean isNameUnique(String name);

    String crypt(String nonCryptPassword);

    boolean isZoneIdExist(String zoneId);

    boolean isLocaleExist(Locale locale);

    User getByName(String name);

    User getByEmail(String email);
}
