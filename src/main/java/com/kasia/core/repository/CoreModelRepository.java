package com.kasia.core.repository;

import com.kasia.core.model.CoreModel;

import java.util.Set;

public interface CoreModelRepository<T extends CoreModel> {
    T saveOrUpdate(T model);

    T get(Long id);

    Boolean delete(Long id);

    Set<T> get();

}
