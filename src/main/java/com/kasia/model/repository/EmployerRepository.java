package com.kasia.model.repository;

import com.kasia.model.Employer;

public interface EmployerRepository extends Repository<Employer> {
    public Employer getByName(String name);
}
