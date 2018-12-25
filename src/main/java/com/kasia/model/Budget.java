package com.kasia.model;

import java.time.LocalDateTime;

public class Budget implements Model{
    private String name;
    private Price balance;
    private LocalDateTime createOn;
    private BudgetCurrency currency;
}
