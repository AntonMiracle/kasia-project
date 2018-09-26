package com.kasia.model;

import com.kasia.validation.budget.BudgetConstraint;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;

@BudgetConstraint
@Entity
@Table(name = "BUDGETS")
public class Budget implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @OneToMany
    @JoinTable(name = "BUDGETS_ARTICLES",
            joinColumns = @JoinColumn(name = "BUDGET_ID"),
            inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
    private Set<Article> articles;
    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;
    @Column(name = "CREATE_ON", nullable = false)
    private LocalDateTime createOn;
    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;

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

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime startOn) {
        this.createOn = startOn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;

        if (id != budget.id) return false;
        if (name != null ? !name.equals(budget.name) : budget.name != null) return false;
        if (articles != null ? !articles.equals(budget.articles) : budget.articles != null) return false;
        if (balance != null ? !balance.equals(budget.balance) : budget.balance != null) return false;
        if (createOn != null ? !createOn.equals(budget.createOn) : budget.createOn != null) return false;
        return currency != null ? currency.equals(budget.currency) : budget.currency == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (articles != null ? articles.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", articles=" + articles +
                ", balance=" + balance +
                ", createOn=" + createOn +
                ", currency=" + currency +
                '}';
    }
}

