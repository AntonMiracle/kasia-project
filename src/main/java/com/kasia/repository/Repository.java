package com.kasia.repository;

import java.util.Set;

public interface Repository<T> {
    void save(T model);

    T getById(long id);

    void delete(T model);

    Set<T> getAll();
}
