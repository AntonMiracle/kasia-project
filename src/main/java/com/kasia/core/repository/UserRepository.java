package com.kasia.core.repository;

import com.kasia.core.model.User;

public interface UserRepository extends ModelRepository<User> {
    User get(String username);

    boolean isUsernameExist(String username);
}
