package com.kasia.model;

import com.kasia.model.repository.converter.BigDecimalAttributeConverter;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class Balance implements Model {
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currencies currencies;
    private LocalDateTime changeOn;

    public Balance(BigDecimal amount, Currencies currencies, LocalDateTime changeOn) {
        this.amount = amount;
        this.currencies = currencies;
        this.changeOn = changeOn;
    }

    public Balance() {
    }

    @Override
    public String toString() {
        return "Balance{" +
                "amount=" + amount +
                ", currencies=" + currencies +
                ", changeOn=" + changeOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (amount != null ? !amount.equals(balance.amount) : balance.amount != null) return false;
        if (currencies != balance.currencies) return false;
        return changeOn != null ? changeOn.equals(balance.changeOn) : balance.changeOn == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currencies != null ? currencies.hashCode() : 0);
        result = 31 * result + (changeOn != null ? changeOn.hashCode() : 0);
        return result;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getChangeOn() {
        return changeOn;
    }

    public void setChangeOn(LocalDateTime changeOn) {
        this.changeOn = changeOn;
    }

    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currency) {
        this.currencies = currency;
    }
}
