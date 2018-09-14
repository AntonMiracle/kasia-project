package com.kasia.service;

import com.kasia.model.Economy;
import com.kasia.model.User;

import javax.xml.bind.ValidationException;

public interface UserService extends ValidationService<User> {
    User create(String email, String password, String nick) throws NullPointerException, ValidationException;

    boolean update(User user) throws ValidationException, NullPointerException, IllegalArgumentException;

    boolean delete(long id) throws IllegalArgumentException;

    User getById(long id) throws IllegalArgumentException;

    User getByEmail(String email) throws NullPointerException, ValidationException;

    User getByNick(String nick) throws NullPointerException, ValidationException;

    boolean addEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException;

    boolean removeEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException;

    String cryptPassword(String password) throws NullPointerException, ValidationException;

    boolean isUserExist(String email, String nick) throws NullPointerException, ValidationException;

}