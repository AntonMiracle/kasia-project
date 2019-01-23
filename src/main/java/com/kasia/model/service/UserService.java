package com.kasia.model.service;

import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ValidationException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

public interface UserService extends UserDetailsService, PasswordEncoder {

    User saveUser(User model) throws EmailExistRuntimeException, UserNameExistRuntimeException, ValidationException, IdInvalidRuntimeException;

    boolean deleteUser(long id);

    User findUserById(long id);

    Set<User> findAllUsers();

    User createUser(String email, String name, String password, String zoneId, String localeLang, String localeCountry) throws ValidationException;

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

    Set<Budget> findOwnBudgets(long userId);

    User findOwner(long budgetId);

    boolean addBudget(long userId, long budgetId);

    boolean removeBudget(long userId, long budgetId);

    Set<User> findConnectUsers(long budgetId);

    boolean isUserOwnBudget(long budgetId, long userId);

    boolean isUserConnectToBudget(long budgetId, long userId);
}
