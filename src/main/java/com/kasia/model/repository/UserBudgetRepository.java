package com.kasia.model.repository;

import com.kasia.model.UserBudget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserBudgetRepository extends CrudRepository<UserBudget, Long> {
    @Query("SELECT ub FROM UserBudget ub WHERE ub.user.id=:id")
    Optional<UserBudget> findByUserId(@Param("id") long id);
}
