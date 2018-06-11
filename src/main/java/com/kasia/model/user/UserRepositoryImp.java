package com.kasia.model.user;

import com.kasia.model.result.Result;
import com.kasia.repository.UserRepository;

import java.util.Set;

public class UserRepositoryImp implements UserRepository {
    @Override
    public Result<User> add(User model) {
        return null;
    }

    @Override
    public Result<Boolean> removeById(long id) {
        return null;
    }

    @Override
    public Result<User> update(User model) {
        return null;
    }

    @Override
    public Result<Set<User>> getAll() {
        return null;
    }

    @Override
    public Result<User> getById(long id) {
        return null;
    }
}
