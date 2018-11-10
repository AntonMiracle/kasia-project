package com.kasia.repository;

import com.kasia.model.Model;

import java.util.Set;

public interface Repository<T extends Model> {

    T getById(long id);

    Set<T> getAll();

    boolean delete(T model);

    T save(T model);
}
