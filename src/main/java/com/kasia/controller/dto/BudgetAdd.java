package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.PriceValid;
import com.kasia.controller.dto.validator.constraint.BudgetNameValid;

@BudgetNameValid(nameFN = "name", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@PriceValid(priceFN = "price"
        , regex = "^[-+]?\\d+|[-+]?\\d+[.,]\\d+|[-+]?\\d+[.,]|[.,]\\d+$"
        , message = "{validation.price.amount.error}")
public class BudgetAdd {
    private String name = "";
    private String price = "0";
    private String currency = "";
    private long userId;

    @Override
    public String toString() {
        return "BudgetAdd{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetAdd budgetAdd = (BudgetAdd) o;

        if (userId != budgetAdd.userId) return false;
        if (name != null ? !name.equals(budgetAdd.name) : budgetAdd.name != null) return false;
        if (price != null ? !price.equals(budgetAdd.price) : budgetAdd.price != null) return false;
        return currency != null ? currency.equals(budgetAdd.currency) : budgetAdd.currency == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
