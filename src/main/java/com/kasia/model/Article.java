package com.kasia.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Article implements Model {
    private String description;
    private Type type;
    private BigDecimal amount;
    private Instant createOn;
}

enum Type {
    INCOME, CONSUMPTION
}
