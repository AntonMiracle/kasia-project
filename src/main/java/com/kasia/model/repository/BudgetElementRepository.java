package com.kasia.model.repository;

import com.kasia.model.BudgetElement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BudgetElementRepository extends CrudRepository<BudgetElement, Long> {
    @Query("SELECT be FROM BudgetElement be WHERE be.budget.id=:id")
    Optional<BudgetElement> findByBudgetId(@Param("id") long id);
}
