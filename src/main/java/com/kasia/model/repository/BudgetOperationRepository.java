package com.kasia.model.repository;

import com.kasia.model.BudgetOperation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BudgetOperationRepository extends CrudRepository<BudgetOperation, Long> {
    @Query("SELECT bo FROM BudgetOperation bo WHERE bo.budget.id=:id")
    Optional<BudgetOperation> findByBudgetId(@Param("id") long id);
}
