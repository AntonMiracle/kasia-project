package com.kasia.repository;

import com.kasia.model.User;

public interface UserRepository extends Repository<User> {

    public User getByEmail(String email);

    public User getByNick(String nick);
}
