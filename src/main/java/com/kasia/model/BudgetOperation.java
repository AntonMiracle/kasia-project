package com.kasia.model;

import java.util.Set;

public class BudgetOperation implements Model{
    private Budget budget;
    private Set<Operation> operations;

    public BudgetOperation() {
    }

    public BudgetOperation(Budget budget, Set<Operation> operations) {
        this.budget = budget;
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetOperation that = (BudgetOperation) o;

        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return operations != null ? operations.equals(that.operations) : that.operations == null;
    }

    @Override
    public int hashCode() {
        int result = budget != null ? budget.hashCode() : 0;
        result = 31 * result + (operations != null ? operations.hashCode() : 0);
        return result;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
