package com.kasia.repository;

import com.kasia.model.Model;

import java.util.Set;

public interface Repository<T extends Model> {
    void save(T model);

    T getById(long id);

    void delete(T model);

    Set<T> getAll();
}
