package com.kasia.model.repository;

import com.kasia.model.Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OperationRepository extends CrudRepository<Operation, Long> {
    @Query("SELECT o FROM Operation o WHERE o.user.id=:id")
    Iterable<Operation> findByUserId(@Param("id") long id);
}
