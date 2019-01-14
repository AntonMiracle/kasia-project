package com.kasia.repository;

import com.kasia.model.BudgetOperation;
import org.springframework.data.repository.CrudRepository;

public interface BudgetOperationRepository extends CrudRepository<BudgetOperation, Long> {
}
