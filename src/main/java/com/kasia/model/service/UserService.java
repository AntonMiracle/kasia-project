package com.kasia.model.service;

import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.User;

import javax.validation.ValidationException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

public interface UserService {

    User saveUser(User model) throws EmailExistRuntimeException, UserNameExistRuntimeException, ValidationException, IdInvalidRuntimeException;

    boolean deleteUser(User model);

    User findUserById(long id);

    Set<User> findAllUsers();

    User createUser(String email, String name, String password, ZoneId zoneId, Locale locale) throws ValidationException;

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

    Set<Budget> findOwnBudgets(User user);

    User findOwner(Budget budget);

    boolean addBudget(User user, Budget budget);

    boolean removeBudget(User user, Budget budget);

    Set<User> findConnectUsers(Budget budget);

    boolean isUserOwnBudget(Budget budget, User user);

    boolean isUserConnectToBudget(Budget budget, User user);
}
