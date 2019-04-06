package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.PriceValid;

import javax.validation.constraints.Size;

@PriceValid(priceFN = "price"
        , regex = "^(0*[1-9]\\d*)|(0*\\d*[.,][0-9]+)$"
        , message = "{validation.price.amount.error}")
public class OperationDTO {
    private long elementId;
    private long placeId;
    private long userId;
    private long budgetId;
    private String price = "0";
    private boolean isStarted;
    @Size(max = 64, message = "{validation.description.error}")
    private String description = "";
    private boolean isIncome;
    private boolean isConsumption;
    private String elementName = "";
    private String placeName = "";
    private String currency = "";
    private String placeDescription = "";

    public OperationDTO() {
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "elementId=" + elementId +
                ", placeId=" + placeId +
                ", userId=" + userId +
                ", budgetId=" + budgetId +
                ", price='" + price + '\'' +
                ", isStarted=" + isStarted +
                ", description='" + description + '\'' +
                ", isIncome=" + isIncome +
                ", isConsumption=" + isConsumption +
                ", elementName='" + elementName + '\'' +
                ", placeName='" + placeName + '\'' +
                ", currency='" + currency + '\'' +
                ", placeDescription='" + placeDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationDTO that = (OperationDTO) o;

        if (elementId != that.elementId) return false;
        if (placeId != that.placeId) return false;
        if (userId != that.userId) return false;
        if (budgetId != that.budgetId) return false;
        if (isStarted != that.isStarted) return false;
        if (isIncome != that.isIncome) return false;
        if (isConsumption != that.isConsumption) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (elementName != null ? !elementName.equals(that.elementName) : that.elementName != null) return false;
        if (placeName != null ? !placeName.equals(that.placeName) : that.placeName != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        return placeDescription != null ? placeDescription.equals(that.placeDescription) : that.placeDescription == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (elementId ^ (elementId >>> 32));
        result = 31 * result + (int) (placeId ^ (placeId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (isStarted ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isIncome ? 1 : 0);
        result = 31 * result + (isConsumption ? 1 : 0);
        result = 31 * result + (elementName != null ? elementName.hashCode() : 0);
        result = 31 * result + (placeName != null ? placeName.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (placeDescription != null ? placeDescription.hashCode() : 0);
        return result;
    }

    public OperationDTO(long elementId, long placeId, long userId, long budgetId, String price, boolean isStarted) {

        this.elementId = elementId;
        this.placeId = placeId;
        this.userId = userId;
        this.budgetId = budgetId;
        this.price = price;
        this.isStarted = isStarted;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public boolean isConsumption() {
        return isConsumption;
    }

    public void setConsumption(boolean consumption) {
        isConsumption = consumption;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }
}
