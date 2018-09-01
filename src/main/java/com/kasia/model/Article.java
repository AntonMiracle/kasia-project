package com.kasia.model;

import com.kasia.validation.article.ArticleConstraint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ArticleConstraint
public class Article implements Model {
    private long id;
    private String description;
    private Type type;
    private BigDecimal amount;
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

    public enum Type {
        INCOME, CONSUMPTION;

    }
}

