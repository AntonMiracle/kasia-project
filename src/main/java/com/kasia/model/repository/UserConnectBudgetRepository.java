package com.kasia.model.repository;

import com.kasia.model.UserConnectBudget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserConnectBudgetRepository extends CrudRepository<UserConnectBudget, Long> {
    @Query("SELECT ucb FROM UserConnectBudget ucb WHERE ucb.user.id=:id")
    Optional<UserConnectBudget> findByUserId(@Param("id") long id);
}
