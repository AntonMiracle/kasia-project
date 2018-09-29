package com.kasia.repository;

import com.kasia.model.Economy;

public interface EconomyRepository {
    Economy getById(long id);

    boolean delete(Economy economy);

    boolean update(Economy economy);

    Economy save(Economy economy);
}
