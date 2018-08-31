package com.kasia.model;

import java.math.BigDecimal;
import java.util.Set;

public class Budget implements Model {
    private String name;
    private Set<Article> articles;
    private BigDecimal amount;
    private BigDecimal balance;

}
