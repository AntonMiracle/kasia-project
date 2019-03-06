package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ElementNameValid;
import com.kasia.controller.dto.validator.constraint.PriceAmountValid;

@ElementNameValid(nameFN = "name", typeFN = "type",min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@PriceAmountValid(balanceFN = "defaultPrice", message = "{validation.price.amount.error}")
public class ElementDTO {
    private String name;
    private String defaultPrice = "0";
    private String type;
    private long budgetId;
    private String currency;

    @Override
    public String toString() {
        return "ElementDTO{" +
                "name='" + name + '\'' +
                ", defaultPrice='" + defaultPrice + '\'' +
                ", type='" + type + '\'' +
                ", budgetId=" + budgetId +
                ", currency='" + currency + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(String defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementDTO that = (ElementDTO) o;

        if (budgetId != that.budgetId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (defaultPrice != null ? !defaultPrice.equals(that.defaultPrice) : that.defaultPrice != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return currency != null ? currency.equals(that.currency) : that.currency == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (defaultPrice != null ? defaultPrice.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    public ElementDTO() {

    }

    public ElementDTO(String name, String defaultPrice, String type, long budgetId, String currency) {

        this.name = name;
        this.defaultPrice = defaultPrice;
        this.type = type;
        this.budgetId = budgetId;
        this.currency = currency;
    }
}
