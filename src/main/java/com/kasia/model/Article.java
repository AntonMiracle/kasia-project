package com.kasia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    public void setId(Long id) {
        this.id = id;
    }


    public enum Type {
        INCOME, CONSUMPTION;

    }
}

