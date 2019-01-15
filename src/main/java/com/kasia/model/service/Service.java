package com.kasia.model.service;

import com.kasia.model.Model;

import java.util.Set;

public interface Service<T extends Model> {
    T save(T model);

    boolean delete(T model);

    T findById(long id);

    Set<T> findAll();
}
