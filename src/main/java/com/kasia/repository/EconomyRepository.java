package com.kasia.repository;

import com.kasia.model.Economy;

public interface EconomyRepository {
    Economy getById(long id);

    boolean delete(Economy article);

    boolean update(Economy article);

    Economy save(Economy article);
}
