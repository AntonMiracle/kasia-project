package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.BalanceValueIsValid;
import com.kasia.controller.dto.validator.constraint.BudgetNameIsValid;

@BudgetNameIsValid(nameFN = "name", message = "{validation.budget.name.error}")
@BalanceValueIsValid(banknotesFN = "banknotes", pennyFN = "penny", message = "{validation.budget.balance.value.error}")
public class AddBudgetDTO {
    private String name;
    private String banknotes;
    private String penny;
    private String currency;
    private String userEmail;

    @Override
    public String toString() {
        return "AddBudgetDTO{" +
                "name='" + name + '\'' +
                ", banknotes='" + banknotes + '\'' +
                ", penny='" + penny + '\'' +
                ", currency='" + currency + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(String banknotes) {
        this.banknotes = banknotes;
    }

    public String getPenny() {
        return penny;
    }

    public void setPenny(String penny) {
        this.penny = penny;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
