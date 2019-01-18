package com.kasia.model;

import com.kasia.model.repository.converter.BigDecimalAttributeConverter;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Embeddable
public class Price implements Model, Comparable<Price> {
    @NotNull
    @Min(0)
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currencies currencies;

    public Price() {
    }

    public Price(BigDecimal amount, Currencies currencies) {
        this.amount = amount;
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", currencies=" + currencies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (amount != null ? !amount.equals(price.amount) : price.amount != null) return false;
        return currencies == price.currencies;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currencies != null ? currencies.hashCode() : 0);
        return result;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currency) {
        this.currencies = currency;
    }

    @Override
    public int compareTo(Price o) {
        return this.amount.compareTo(o.getAmount());
    }
}
