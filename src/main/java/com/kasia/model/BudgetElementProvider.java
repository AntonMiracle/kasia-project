package com.kasia.model;

import java.util.Set;

public class BudgetElementProvider implements Model{
    private Budget budget;
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

        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return elementProviders != null ? elementProviders.equals(that.elementProviders) : that.elementProviders == null;
    }

    @Override
    public int hashCode() {
        int result = budget != null ? budget.hashCode() : 0;
        result = 31 * result + (elementProviders != null ? elementProviders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetElementProvider{" +
                "budget=" + budget +
                ", elementProviders=" + elementProviders +
                '}';
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
