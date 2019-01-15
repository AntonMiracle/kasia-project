package com.kasia.model;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Embeddable
public class Balance {
    private BigDecimal amount;
    private Currency currency;
    private LocalDateTime changeOn;

    public Balance(BigDecimal amount, Currency currency, LocalDateTime changeOn) {
        this.amount = amount;
        this.currency = currency;
        this.changeOn = changeOn;
    }

    public Balance() {
    }

    @Override
    public String toString() {
        return "Balance{" +
                "amount=" + amount +
                ", currency=" + currency +
                ", changeOn=" + changeOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (amount != null ? !amount.equals(balance.amount) : balance.amount != null) return false;
        if (currency != null ? !currency.equals(balance.currency) : balance.currency != null) return false;
        return changeOn != null ? changeOn.equals(balance.changeOn) : balance.changeOn == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (changeOn != null ? changeOn.hashCode() : 0);
        return result;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getChangeOn() {
        return changeOn;
    }

    public void setChangeOn(LocalDateTime changeOn) {
        this.changeOn = changeOn;
    }
}
