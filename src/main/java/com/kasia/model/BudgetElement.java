package com.kasia.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BudgetElement implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Budget budget;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Element> elements = new HashSet<>();

    public BudgetElement() {
    }

    public BudgetElement(Budget budget, Set<Element> elements) {
        this.budget = budget;
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "BudgetElement{" +
                "id=" + id +
                ", budget=" + budget +
                ", elements=" + elements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetElement that = (BudgetElement) o;

        if (id != that.id) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return elements != null ? elements.equals(that.elements) : that.elements == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
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

    public Set<Element> getElements() {
        return elements;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }
}
