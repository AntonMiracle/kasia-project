package com.kasia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Set;

public class Economy implements Model {
    private long id;
    private String name;
    private Set<Budget> budgets;
    private LocalDateTime startOn;
    private BigDecimal balance;
    private Currency currency;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
