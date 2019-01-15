package com.kasia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class BudgetElementProvider implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Budget budget;
    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ElementProvider> elementProviders;

    public BudgetElementProvider() {
    }

    public BudgetElementProvider(Budget budget, Set<ElementProvider> elementProviders) {
        this.budget = budget;
        this.elementProviders = elementProviders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetElementProvider that = (BudgetElementProvider) o;

        if (id != that.id) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return elementProviders != null ? elementProviders.equals(that.elementProviders) : that.elementProviders == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (elementProviders != null ? elementProviders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetElementProvider{" +
                "id=" + id +
                ", budget=" + budget +
                ", elementProviders=" + elementProviders +
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

    public Set<ElementProvider> getElementProviders() {
        return elementProviders;
    }

    public void setElementProviders(Set<ElementProvider> elementProviders) {
        this.elementProviders = elementProviders;
    }
}
