package com.kasia.model.service;

import com.kasia.model.Budget;
import com.kasia.model.User;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

public interface UserService {

    User save(User model);

    boolean delete(User model);

    User findUserById(long id);

    Set<User> findAllUsers();

    User createUser(String email, String name, String password, ZoneId zoneId, Locale locale);

    boolean isUserEmailUnique(String email);

    boolean isUserNameUnique(String name);

    String cryptPassword(String nonCryptPassword);

    ZoneId zoneIdOf(String zoneId);

    Locale localeOf(String lang, String country);

    User findUserByName(String name);

    User findUserByEmail(String email);

    boolean isActivated(User user);

    boolean activate(User user);

    boolean deactivate(User user);

    Set<Locale> getCorrectAvailableLocales();

    boolean isLocaleAvailable(Locale locale);

    Locale getDefaultLocale();

    Set<Budget> findAllOwnBudget(User user);

    boolean addBudget(User user, Budget budget);

    boolean removeBudget(User user, Budget budget);

    Set<User> findAllConnectedUser(Budget budget);

    boolean isUserOwnerOfBudget(User user);

    boolean isUserConnectedToBudget(User user);
}
