package com.kasia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class BudgetOperation implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Budget budget;
    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
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

        if (id != that.id) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return operations != null ? operations.equals(that.operations) : that.operations == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (operations != null ? operations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetOperation{" +
                "id=" + id +
                ", budget=" + budget +
                ", operations=" + operations +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
