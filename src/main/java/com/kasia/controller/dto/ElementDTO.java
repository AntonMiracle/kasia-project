package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ElementNameValid;
import com.kasia.controller.dto.validator.constraint.PriceValid;

@ElementNameValid(nameFN = "name", typeFN = "type", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@PriceValid(priceFN = "price"
        , regex = "^\\d+|\\d+[.,]\\d+|\\d+[.,]|[.,]\\d+$"
        , message = "{validation.price.amount.error}")
public class ElementDTO {
    private String name = "";
    private String price = "0";
    private long budgetId;
    private long elementId;
    private long userId;
    private String currency = "";
    private boolean canBeDeleted;
    private String oldName = "";

    @Override
    public String toString() {
        return "ElementDTO{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", budgetId=" + budgetId +
                ", elementId=" + elementId +
                ", userId=" + userId +
                ", currency='" + currency + '\'' +
                ", canBeDeleted=" + canBeDeleted +
                ", oldName='" + oldName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public long getElementId() {
        return elementId;
    }

    public void setElementId(long elementId) {
        this.elementId = elementId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
