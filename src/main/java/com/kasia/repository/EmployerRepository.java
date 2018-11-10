package com.kasia.repository;

import com.kasia.model.Employer;

public interface EmployerRepository extends Repository<Employer> {
    public Employer getByName(String name);
}
