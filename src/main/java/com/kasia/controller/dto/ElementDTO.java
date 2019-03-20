package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ElementNameValid;
import com.kasia.controller.dto.validator.constraint.PriceAmountValid;

@ElementNameValid(nameFN = "name", typeFN = "type", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@PriceAmountValid(priceFN = "defaultPrice"
        , regex = "^\\d+|\\d+[.,]\\d+|\\d+[.,]|[.,]\\d+$"
        , message = "{validation.price.amount.error}")
public class ElementDTO {
    private String name;
    private String defaultPrice = "0";
    private String type;
    private long budgetId;
    private String currency;
    private boolean canBeDeleted;
    private long id;
    private String oldName;

    @Override
    public String toString() {
        return "ElementDTO{" +
                "name='" + name + '\'' +
                ", defaultPrice='" + defaultPrice + '\'' +
                ", type='" + type + '\'' +
                ", budgetId=" + budgetId +
                ", currency='" + currency + '\'' +
                ", canBeDeleted=" + canBeDeleted +
                ", id=" + id +
                ", oldName='" + oldName + '\'' +
                '}';
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
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

    public boolean isCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
