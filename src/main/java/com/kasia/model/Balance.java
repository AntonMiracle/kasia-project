package com.kasia.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class Balance implements Model {
    @NotNull
    private BigDecimal amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currencies currency;
    @NotNull
    @PastOrPresent
    private LocalDateTime changeOn;

    public Balance(BigDecimal amount, Currencies currency, LocalDateTime changeOn) {
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
        if (currency != balance.currency) return false;
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

    public LocalDateTime getChangeOn() {
        return changeOn;
    }

    public void setChangeOn(LocalDateTime changeOn) {
        this.changeOn = changeOn;
    }

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }
}
