package com.kasia.model.service;

import com.kasia.model.User;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

public interface UserService extends Service<User> {
    User create(String email, String name, String password, ZoneId zoneId, Locale locale);

    boolean isEmailUnique(String email);

    boolean isNameUnique(String name);

    String crypt(String nonCryptPassword);

    ZoneId zoneIdOf(String zoneId);

    Locale localeOf(String lang, String country);

    User findByName(String name);

    User findByEmail(String email);

    boolean isActivated(User user);

    boolean activate(User user);

    boolean deactivate(User user);

    Set<Locale> getCorrectAvailableLocales();

}
