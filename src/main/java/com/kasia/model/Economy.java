package com.kasia.model;

import com.kasia.repository.converter.LocalDateTimeAttributeConverter;
import com.kasia.validation.economy.EconomyConstraint;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@EconomyConstraint
@Entity
@Table(name = "ECONOMIES")
public class Economy implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @OneToMany
    @JoinTable(name = "ECONOMIES_BUDGETS",
            joinColumns = @JoinColumn(name = "ECONOMY_ID"),
            inverseJoinColumns = @JoinColumn(name = "BUDGET_ID"))
    private Set<Budget> budgets;
    @Column(name = "CREATE_ON", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createOn;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Set<Budget> budgets) {
        this.budgets = budgets;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime startOn) {
        this.createOn = startOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Economy economy = (Economy) o;

        if (id != economy.id) return false;
        if (name != null ? !name.equals(economy.name) : economy.name != null) return false;
        if (budgets != null ? !budgets.equals(economy.budgets) : economy.budgets != null) return false;
        return createOn != null ? createOn.equals(economy.createOn) : economy.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (budgets != null ? budgets.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Economy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budgets=" + budgets +
                ", createOn=" + createOn +
                '}';
    }
}
