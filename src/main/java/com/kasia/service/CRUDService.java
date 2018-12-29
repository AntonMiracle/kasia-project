package com.kasia.service;

import com.kasia.model.Model;

public interface CRUDService<T extends Model> {
    T save(T model);

    boolean delete(T model);

    T getById(long id);
}
