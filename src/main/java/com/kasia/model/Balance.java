package com.kasia.model;

import com.kasia.model.repository.converter.BigDecimalAttributeConverter;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class Balance implements Model {
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal amount;
    private String currency;
    private LocalDateTime updateOn;

    public Balance(BigDecimal amount, String currency, LocalDateTime updateOn) {
        this.amount = amount;
        this.currency = currency;
        this.updateOn = updateOn;
    }

    public Balance() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (amount != null ? !amount.equals(balance.amount) : balance.amount != null) return false;
        if (currency != null ? !currency.equals(balance.currency) : balance.currency != null) return false;
        return updateOn != null ? updateOn.equals(balance.updateOn) : balance.updateOn == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (updateOn != null ? updateOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", updateOn=" + updateOn +
                '}';
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(LocalDateTime updateOn) {
        this.updateOn = updateOn;
    }
}
