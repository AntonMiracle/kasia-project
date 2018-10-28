package com.kasia.service;

import com.kasia.model.Economy;
import com.kasia.model.User;

import javax.xml.bind.ValidationException;
import java.time.ZoneId;
import java.util.Set;

public interface UserService extends ValidationService<User> {
    User create(String email, String password, String nick, ZoneId zoneId) throws NullPointerException, ValidationException;

    boolean update(User user) throws ValidationException, NullPointerException, IllegalArgumentException;

    boolean delete(long id) throws IllegalArgumentException;

    User getUserById(long id) throws IllegalArgumentException;

    User getByEmail(String email) throws NullPointerException, ValidationException;

    User getByNick(String nick) throws NullPointerException, ValidationException;

    boolean addEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException;

    boolean removeEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException;

    String cryptPassword(String password) throws NullPointerException, ValidationException;

    Set<User> getAll();
}
