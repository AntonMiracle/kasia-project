package com.kasia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class Budget implements Model {
    private long id;
    private String name;
    private Set<Article> articles;
    private BigDecimal balance;
    private LocalDateTime startOn;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}

