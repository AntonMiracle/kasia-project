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

    boolean deleteUser(User model) throws ValidationException, IdInvalidRuntimeException;

    User findUserById(long id) throws IdInvalidRuntimeException;

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

    boolean activate(User user) throws ValidationException;

    boolean deactivate(User user);

    Set<Locale> getCorrectAvailableLocales();

    boolean isLocaleAvailable(Locale locale);

    Locale getDefaultLocale();

    Set<Budget> findAllOwnBudget(User user);

    boolean addBudget(User user, Budget budget);

    boolean removeBudget(User user, Budget budget);

    Set<User> findAllConnectedUser(Budget budget);

    boolean isUserOwnerOfBudget(Budget budget, User user);

    boolean isUserConnectedToBudget(Budget budget, User user);
}
