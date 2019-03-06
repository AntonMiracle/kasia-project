package com.kasia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class BudgetProvider implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Budget budget;
    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Provider> providers;

    public BudgetProvider() {
    }

    public BudgetProvider(Budget budget, Set<Provider> providers) {
        this.budget = budget;
        this.providers = providers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetProvider that = (BudgetProvider) o;

        if (id != that.id) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return providers != null ? providers.equals(that.providers) : that.providers == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (providers != null ? providers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetProvider{" +
                "id=" + id +
                ", budget=" + budget +
                ", providers=" + providers +
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

    public Set<Provider> getProviders() {
        return providers;
    }

    public void setProviders(Set<Provider> providers) {
        this.providers = providers;
    }
}