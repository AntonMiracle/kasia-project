package com.kasia.repository;

import com.kasia.model.User;

import java.util.Set;

public interface UserRepository {

    User getById(long id);

    User getByEmail(String email);

    User getByNick(String nick);

    boolean delete(User user);

    boolean update(User user);

    User save(User user);

    Set<User> getAll();
}
