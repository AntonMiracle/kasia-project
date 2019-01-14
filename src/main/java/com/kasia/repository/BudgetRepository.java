package com.kasia.repository;

import com.kasia.model.Budget;
import org.springframework.data.repository.CrudRepository;

public interface BudgetRepository extends CrudRepository<Budget, Long> {

}
