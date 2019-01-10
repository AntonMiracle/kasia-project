package com.kasia.repository;

import com.kasia.model.User;

public interface UserRepository extends Repository<User> {
    User getByEmail(String email);
    User getByName(String name);
}
