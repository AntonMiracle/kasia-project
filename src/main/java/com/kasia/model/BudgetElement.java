package com.kasia.model;

import java.util.Set;

public class BudgetElement implements Model {
    private Budget budget;
    private Set<Element> elements;

    public BudgetElement() {
    }

    public BudgetElement(Budget budget, Set<Element> elements) {
        this.budget = budget;
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetElement that = (BudgetElement) o;

        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return elements != null ? elements.equals(that.elements) : that.elements == null;
    }

    @Override
    public int hashCode() {
        int result = budget != null ? budget.hashCode() : 0;
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Set<Element> getElements() {
        return elements;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }
}
