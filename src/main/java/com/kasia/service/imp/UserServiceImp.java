package com.kasia.service.imp;

import com.kasia.model.Economy;
import com.kasia.model.User;
import com.kasia.repository.UserRepository;
import com.kasia.service.UserService;

import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;

public class UserServiceImp implements UserService {
    private UserRepository repository;

    @Override
    public User create(String email, String password, String nick) throws NullPointerException, ValidationException {
        return null;
    }

    @Override
    public boolean update(User user) throws ValidationException, NullPointerException, IllegalArgumentException {
        return false;
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        return false;
    }

    @Override
    public User getById(long id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public User getByEmail(String email) throws NullPointerException, ValidationException {
        return null;
    }

    @Override
    public User getByNick(String nick) throws NullPointerException, ValidationException {
        return null;
    }

    @Override
    public boolean addEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException {
        return false;
    }

    @Override
    public boolean removeEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException {
        return false;
    }

    @Override
    public String cryptPassword(String password) throws NullPointerException, ValidationException {
        return null;
    }

    @Override
    public boolean isUserExist(String email, String nick) throws NullPointerException, ValidationException {
        return false;
    }

    @Override
    public boolean isValid(User user) throws NullPointerException {
        if (user == null) throw new NullPointerException();

        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(user).size() == 0;
        }
    }
}
