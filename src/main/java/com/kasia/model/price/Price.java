package com.kasia.model.price;

import com.kasia.validator.price.PriceConstraint;

import java.io.Serializable;

@PriceConstraint
public class Price implements Serializable {
    private long amount;

    public Price(long amount) {
        this.amount = amount;
    }

    public Price() {

    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        return amount == price.amount;
    }

    @Override
    public int hashCode() {
        return (int) (amount ^ (amount >>> 32));
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                '}';
    }
}
