package com.kasia.model.repository;

import com.kasia.model.BudgetPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BudgetPlaceRepository extends CrudRepository<BudgetPlace, Long> {
    @Query("SELECT bep FROM BudgetPlace bep WHERE bep.budget.id=:id")
    Optional<BudgetPlace> findByBudgetId(@Param("id") long id);
}
