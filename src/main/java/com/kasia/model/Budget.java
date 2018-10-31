package com.kasia.model;

import com.kasia.repository.converter.BigDecimalAttributeConverter;
import com.kasia.repository.converter.LocalDateTimeAttributeConverter;
import com.kasia.validation.budget.BudgetConstraint;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;

@BudgetConstraint
@Entity
@Table(name = "BUDGETS")
public class Budget implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BUDGETS_ARTICLES",
            joinColumns = @JoinColumn(name = "BUDGET_ID"),
            inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
    private Set<Article> articles;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BUDGETS_OPERATIONS",
            joinColumns = @JoinColumn(name = "BUDGET_ID"),
            inverseJoinColumns = @JoinColumn(name = "OPERATION_ID"))
    private Set<Operation> operations;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BUDGETS_EMPLOYERS",
            joinColumns = @JoinColumn(name = "BUDGET_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYER_ID"))
    private Set<Employer> employers;
    @Column(name = "BALANCE", nullable = false)
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal balance;
    @Column(name = "CREATE_ON", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createOn;
    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;

    public Budget() {
    }

    public Budget(String name, BigDecimal balance, Currency currency, LocalDateTime createOn) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.createOn = createOn;
    }

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

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public Set<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers(Set<Employer> employers) {
        this.employers = employers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;

        if (id != budget.id) return false;
        if (name != null ? !name.equals(budget.name) : budget.name != null) return false;
        if (articles != null ? !articles.equals(budget.articles) : budget.articles != null) return false;
        if (operations != null ? !operations.equals(budget.operations) : budget.operations != null) return false;
        if (employers != null ? !employers.equals(budget.employers) : budget.employers != null) return false;
        if (balance != null ? !balance.equals(budget.balance) : budget.balance != null) return false;
        if (createOn != null ? !createOn.equals(budget.createOn) : budget.createOn != null) return false;
        return currency != null ? currency.equals(budget.currency) : budget.currency == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (articles != null ? articles.hashCode() : 0);
        result = 31 * result + (operations != null ? operations.hashCode() : 0);
        result = 31 * result + (employers != null ? employers.hashCode() : 0);
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
                ", operations=" + operations +
                ", employers=" + employers +
                ", balance=" + balance +
                ", createOn=" + createOn +
                ", currency=" + currency +
                '}';
    }
}

