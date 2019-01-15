package com.kasia.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Budget implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Pattern(regexp = "^\\S{2,16}$")
    private String name;
    @NotNull
    @Embedded
    private Price balance;
    @NotNull
    @PastOrPresent
    private LocalDateTime createOn;

    public Budget(String name, Price balance, LocalDateTime createOn) {
        this.name = name;
        this.balance = balance;
        this.createOn = createOn;
    }

    public Budget() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;

        if (id != budget.id) return false;
        if (name != null ? !name.equals(budget.name) : budget.name != null) return false;
        if (balance != null ? !balance.equals(budget.balance) : budget.balance != null) return false;
        return createOn != null ? createOn.equals(budget.createOn) : budget.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", createOn=" + createOn +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getBalance() {
        return balance;
    }

    public void setBalance(Price balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
