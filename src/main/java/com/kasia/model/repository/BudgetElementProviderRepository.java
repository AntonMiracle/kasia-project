package com.kasia.model.repository;

import com.kasia.model.BudgetElementProvider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BudgetElementProviderRepository extends CrudRepository<BudgetElementProvider, Long> {
    @Query("SELECT bep FROM BudgetElementProvider bep WHERE bep.budget.id=:id")
    Optional<BudgetElementProvider> findByBudgetId(@Param("id") long id);
}
