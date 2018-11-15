package com.kasia.model;

import com.kasia.repository.converter.BigDecimalAttributeConverter;
import com.kasia.repository.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "OPERATION")
public class Operation implements Model {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal amount;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_ID", nullable = false)
    private Article article;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYER_ID", nullable = false)
    private Employer employer;

    @NotNull
    @Past
    @Column(name = "CREATE_ON", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createOn;

    public Operation() {
    }

    public Operation(BigDecimal amount, Article article, User user, Employer employer, LocalDateTime createOn) {
        this.amount = amount;
        this.article = article;
        this.user = user;
        this.employer = employer;
        this.createOn = createOn;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", amount=" + amount +
                ", article=" + article +
                ", user=" + user +
                ", employer=" + employer +
                ", createOn=" + createOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (amount != null ? !amount.equals(operation.amount) : operation.amount != null) return false;
        if (article != null ? !article.equals(operation.article) : operation.article != null) return false;
        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (employer != null ? !employer.equals(operation.employer) : operation.employer != null) return false;
        return createOn != null ? createOn.equals(operation.createOn) : operation.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (article != null ? article.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (employer != null ? employer.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User operator) {
        this.user = operator;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }
}
