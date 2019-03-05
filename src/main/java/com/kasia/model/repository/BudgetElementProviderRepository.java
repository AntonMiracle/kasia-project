package com.kasia.model.repository;

import com.kasia.model.BudgetProvider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BudgetElementProviderRepository extends CrudRepository<BudgetProvider, Long> {
    @Query("SELECT bep FROM BudgetProvider bep WHERE bep.budget.id=:id")
    Optional<BudgetProvider> findByBudgetId(@Param("id") long id);
}
