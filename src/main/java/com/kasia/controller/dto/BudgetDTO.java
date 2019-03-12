package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.PriceAmountValid;
import com.kasia.controller.dto.validator.constraint.BudgetNameValid;

@BudgetNameValid(nameFN = "name", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@PriceAmountValid(priceFN = "balanceInit"
        , regex = "^[-+]?\\d+|[-+]?\\d+[.,]\\d+|[-+]?\\d+[.,]|[.,]\\d+$"
        , message = "{validation.price.amount.error}")
public class BudgetDTO {
    private String name;
    private String balanceInit = "0";
    private String currency;
    private String userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetDTO that = (BudgetDTO) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (balanceInit != null ? !balanceInit.equals(that.balanceInit) : that.balanceInit != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        return userEmail != null ? userEmail.equals(that.userEmail) : that.userEmail == null;
    }

    @Override
    public String toString() {
        return "BudgetDTO{" +
                "name='" + name + '\'' +
                ", balanceInit='" + balanceInit + '\'' +
                ", currency='" + currency + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (balanceInit != null ? balanceInit.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBalanceInit() {
        return balanceInit;
    }

    public void setBalanceInit(String balanceInit) {
        this.balanceInit = balanceInit;
    }
}
