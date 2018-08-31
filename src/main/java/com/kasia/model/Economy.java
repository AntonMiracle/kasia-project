package com.kasia.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Set;

public class Economy implements Model {
    private String name;
    private Set<Budget> budgets;
    private Instant startOn;
    private BigDecimal balance;
    private Currency currency;

}
