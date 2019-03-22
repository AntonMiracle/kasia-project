package com.kasia.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BudgetPlace implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Budget budget;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Place> places = new HashSet<>();

    public BudgetPlace() {
    }

    public BudgetPlace(Budget budget, Set<Place> places) {
        this.budget = budget;
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetPlace that = (BudgetPlace) o;

        if (id != that.id) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return places != null ? places.equals(that.places) : that.places == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (places != null ? places.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BudgetPlace{" +
                "id=" + id +
                ", budget=" + budget +
                ", places=" + places +
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

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }
}
