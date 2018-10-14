package com.kasia.model;

import com.kasia.repository.converter.BigDecimalAttributeConverter;
import com.kasia.repository.converter.LocalDateTimeAttributeConverter;
import com.kasia.validation.article.ArticleConstraint;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ArticleConstraint
@Entity
@Table(name = "ARTICLES")
public class Article implements Model{
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TYPE", nullable = false)
    private Type type;
    @Column(name = "AMOUNT", nullable = false)
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal amount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (id != article.id) return false;
        if (description != null ? !description.equals(article.description) : article.description != null) return false;
        if (type != article.type) return false;
        if (amount != null ? !amount.equals(article.amount) : article.amount != null) return false;
        return createOn != null ? createOn.equals(article.createOn) : article.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", createOn=" + createOn +
                '}';
    }

    public enum Type {
        INCOME, CONSUMPTION

    }
}

