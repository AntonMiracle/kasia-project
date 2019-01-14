package com.kasia.repository;

import com.kasia.model.UserBudget;
import org.springframework.data.repository.CrudRepository;

public interface UserBudgetRepository extends CrudRepository<UserBudget, Long> {
}
