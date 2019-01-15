package com.kasia.model.service;

import com.kasia.model.Model;

public interface Service<T extends Model> {
    T save(T model);

    boolean delete(T model);

    T getById(long id);
}
