package com.kasia.core.repository;

import com.kasia.core.model.Model;

import java.util.Set;

public interface ModelRepository<T extends Model> {
    T saveOrUpdate(T model);

    T get(Long id);

    boolean delete(Long id);

    Set<T> get();

}
