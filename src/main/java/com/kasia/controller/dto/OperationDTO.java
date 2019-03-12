package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.PriceAmountValid;

@PriceAmountValid(priceFN = "price", message = "{validation.price.amount.error}")
public class OperationDTO {
    private long elementId;
    private long providerId;
    private long userId;
    private long budgetId;
    private String price = "0";
    private boolean isStarted;

    public OperationDTO() {
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "elementId=" + elementId +
                ", providerId=" + providerId +
                ", userId=" + userId +
                ", budgetId=" + budgetId +
                ", price='" + price + '\'' +
                ", isStarted=" + isStarted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationDTO that = (OperationDTO) o;

        if (elementId != that.elementId) return false;
        if (providerId != that.providerId) return false;
        if (userId != that.userId) return false;
        if (budgetId != that.budgetId) return false;
        if (isStarted != that.isStarted) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (elementId ^ (elementId >>> 32));
        result = 31 * result + (int) (providerId ^ (providerId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (isStarted ? 1 : 0);
        return result;
    }

    public OperationDTO(long elementId, long providerId, long userId, long budgetId, String price, boolean isStarted) {

        this.elementId = elementId;
        this.providerId = providerId;
        this.userId = userId;
        this.budgetId = budgetId;
        this.price = price;
        this.isStarted = isStarted;
    }

    public long getElementId() {
        return elementId;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void setElementId(long elementId) {
        this.elementId = elementId;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
