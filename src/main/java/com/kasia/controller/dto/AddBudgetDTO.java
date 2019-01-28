package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.BudgetNameIsValid;

@BudgetNameIsValid(nameFN = "name", message = "{validation.budget.name.error}")
public class AddBudgetDTO {
    private String name;
    private String balance;
    private String currency;
    private String userEmail;

    @Override
    public String toString() {
        return "AddBudgetDTO{" +
                "name='" + name + '\'' +
                ", balance='" + balance + '\'' +
                ", currency='" + currency + '\'' +
                ", userEmail=" + userEmail +
                '}';
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
