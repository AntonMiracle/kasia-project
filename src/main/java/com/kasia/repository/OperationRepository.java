package com.kasia.repository;

import com.kasia.model.Operation;

import java.util.Set;

public interface OperationRepository extends Repository<Operation> {

    public Set<Operation> getByUserId(long id);

    public Set<Operation> getByEmployerId(long id);

    public Set<Operation> getByArticleId(long id);

}
